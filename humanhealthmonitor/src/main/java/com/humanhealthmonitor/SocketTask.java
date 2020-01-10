package com.humanhealthmonitor;

import com.github.qcloudsms.httpclient.HTTPException;
import com.humanhealthmonitor.pojo.*;
import com.humanhealthmonitor.service.*;
import com.humanhealthmonitor.pojo.Equipment;
import com.humanhealthmonitor.service.*;

import java.io.*;
//import java.lang.Object;
import java.math.BigInteger;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.el.stream.StreamELResolverImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.humanhealthmonitor.MsgQueue.*;
import static com.humanhealthmonitor.util.ByteUtils.*;

/**
 * 用来处理Socket请求
 */
@Component
public class SocketTask implements Runnable {

    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private AlarmNormalValueService alarmNormalValueService;
    @Autowired
    private AlarmSpecialValueService alarmSpecialValueService;
    @Autowired
    private ObjectService objectService;
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectResouceUseService objectResouceUseService;
    @Autowired
    private AlarmLogService alarmLogService;

    @Autowired
    private UserNetmaskService userNetmaskService;
    @Autowired
    private DataService dataService;

    private static SocketTask socketTask;////////added0521//静态私有化变量，所有类共享一份
    private int taskNum = 0;//任务号初始化为0//added0523
    private InfluxDBConnector influxDBConnector;//创建influxDB连接实例
    private CloudMsgUtil cloudMsgUtil = new CloudMsgUtil();//云短信工具
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Socket socket;
    public int getTaskNum() {
        return this.taskNum;
    }
    public void setTaskNum(int taskNum) {
        this.taskNum = taskNum;
    }
    public void setSocket(Socket socket){
        this.socket = socket;
    }
    public Socket getSocket() {
        return this.socket;
    }
    @PostConstruct
    public void init() {//added0521
        socketTask= this;
        socketTask.equipmentService= this.equipmentService;
        //added
        socketTask.alarmNormalValueService=this.alarmNormalValueService;
        socketTask.alarmSpecialValueService=this.alarmSpecialValueService;
        socketTask.objectService=this.objectService;
        socketTask.userService=this.userService;
        socketTask.objectResouceUseService = this.objectResouceUseService;
        socketTask.alarmLogService=this.alarmLogService;

        socketTask.userNetmaskService=this.userNetmaskService;
        socketTask.dataService=this.dataService;

    }
    public void run() {
        try {
            //socket = HumanhealthmonitorApplication.serverSocket.accept();////////////////
            handleSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 与客户端Socket进行通信
    private void handleSocket() throws Exception {
        //HealthDataProcessor healthDataProcessor = new HealthDataProcessor();//实例化信息处理类
        //如果收到了信息就把信息打印出来
        System.out.println("SocketTask: taskNum: "+this.getTaskNum());

        protocolState[taskNum-1] = 1;//修改协议状态为MODBUS，表明网关已经连接且使用MODBUS//added0526
        System.out.println("SocketTask: change protocolState of netMask"+taskNum + " to 1(MODBUS)...");

        PrintWriter pw;
        System.out.println("SocketTask: "+socket.getInetAddress() + " transferred to socketTask...");
        pw = new PrintWriter(socket.getOutputStream());


        while (sendMsgQueue.get(taskNum - 1).size() == 0) {//为空则线程休眠//modified0524
            Thread.sleep(1000);//1秒
        }
        String orderString = sendMsgQueue.get(taskNum-1).poll();//modified0524

        byte[] orderByte = toByteArray(orderString);

        OutputStream os = socket.getOutputStream();
        os.write(orderByte);
        os.flush();
        System.out.println("SocketTask"+taskNum+": send: " + bytesToHexString(orderByte));
        //字节读取
        //装饰流BufferedReader封装输入流（接收客户端的流）
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        DataInputStream dis = new DataInputStream(bis);
        byte[] bytes = new byte[1]; // 一次读取一个byte
        String info = "";
        List<Byte> byteArrayList = new ArrayList<>();//字节列表
        int netMaskId = 1;

        SocketTaskSender socketTaskSender = new SocketTaskSender(); // added0524
        socketTaskSender.setSocket(socket); // added0524
        socketTaskSender.setTaskNum(netMaskId);
        new Thread(socketTaskSender).start(); // added0524


        while (dis.read(bytes) != -1) {//如何实现循环接收的呢？忘了。。
            info += bytesToHexString(bytes) + " ";//转为16进制字符串
            byteArrayList.add(bytes[0]);//字节列表
            //System.out.print("dis.available(): "+dis.available() + "; ");
            //System.out.print("sendMsgQueue： ");
            //for(String s: sendMsgQueue.get(taskNum - 1)) {
            //    System.out.print(s+ " ");
            //}
            //System.out.println(";");
            if (dis.available() == 0) { //客户端一条信息结束
                System.out.println("SocketTask"+taskNum+": received: " + info);
                System.out.println("SocketTask"+taskNum+": byteArrayList: " + byteArrayList);
                socketInfoPreProcess(byteArrayList);

                info = "";//将info清空
                byteArrayList.clear();//字节列表清空
                //orderString = "FEFE0401040005AABB";


            }
        }

        System.out.println("dis.read(bytes) == -1 退出循环接收");

        //added0525
        String inetAddressStr = socket.getInetAddress().toString();
        Iterator iterator = inetAddressArray.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(inetAddressStr)) {
                iterator.remove();
                break;
            }
        }
        System.out.println("Socket关闭");

        socket.shutdownInput();//added 2019/04/08关闭输入流
        socket.shutdownOutput();//added 2019/04/10关闭输出流

        //关闭相对应的资源
        pw.close();
        bis.close();
        dis.close();
        socket.close();
    }

    //处理不规范的数据
    private void socketInfoPreProcess(List<Byte> byteArrayListPre) {

        System.out.println("<SocketTask:socketinfopreprocess>");

        if(byteArrayListPre.size() < 8) return;
        System.out.println("size(): " + byteArrayListPre.size());
        int low = 0;
        int high = 0;

        while (low < byteArrayListPre.size()) {
            //System.out.println("1 low/high: " + low + " " + high);
            if (byteArrayListPre.get(low) == (byte) 0xFE) {
                if(low + 1 < byteArrayListPre.size() && byteArrayListPre.get(low + 1) == (byte) 0xFE) {
                    high = low + 2;
                    while (high < byteArrayListPre.size()) {
                        //System.out.println("2 low/high: " + low + " " + high);
                        if (byteArrayListPre.get(high) == (byte) 0xAA) {
                            if(high + 1 < byteArrayListPre.size() && byteArrayListPre.get(high + 1) == (byte) 0xBB) {
                                if(low < high) {
                                    List<Byte> byteArrayList = new ArrayList<>();
                                    for(int i = low; i <= high+1; ++i) {
                                        byteArrayList.add(byteArrayListPre.get(i));
                                    }
                                    for(Byte b: byteArrayList) {
                                        System.out.print(Integer.toHexString(b & 0xFF) + " ");
                                    }
                                    System.out.println("<SocketTask:socketinfopreprocess:>" + byteArrayList.size());
                                    socketInfoProcess(byteArrayList);

                                    low = high + 2 - 1; //后面还有个low++，所以-1
                                    break;
                                }

                            }
                        }
                        high++;
                    }
                }
            }
            low++;
        }

    }




    //处理Socket收到的信息
    private void socketInfoProcess(List<Byte> byteArrayList) {
        String date = dateformat.format(System.currentTimeMillis());
        String yearMonth = date.substring(0,7);//如2019-03


        Equipment equipmentData;//added0521
        // 从网关发来的只带一个字节有效数据的帧长度就是8，比这个小的就是坏掉的或无关的
        while (byteArrayList.size() >= 8) {
            int orderType = byteToUnsignedValue(byteArrayList.get(2)); // 指令码
            int responseLength = byteToUnsignedValue(byteArrayList.get(3)); // 回复内容长度
            System.out.println("指令:返回内容长度:" + responseLength);
            byte[] responseContent = new byte[responseLength];  // 回复信息就是不包括校验和(不用扣掉1位校验和)
            int checkSum = (byteArrayList.get(byteArrayList.size()-3) + 256) % 256; // 校验和



            if (byteArrayList.get(0) != (byte) 0xFE || byteArrayList.get(1) != (byte) 0xFE) {
                System.out.println("SocketTask: The byte head is not FEFE");
                byteArrayList.remove(0);
                break;
            }
            if (byteArrayList.size() != responseLength + 7) {
                System.out.println("SocketTask: The byte length is wrong");
                byteArrayList.remove(0);
                break;
            }
            if (byteArrayList.get(byteArrayList.size() - 2) != (byte) 0xAA || byteArrayList.get(byteArrayList.size() - 1) != (byte) 0xBB) {
                System.out.println("SocketTask: The byte tale is not AABB");
                byteArrayList.remove(0);
                break;
            }

            // 将回复信息放到responseContent
            for (int i = 0; i < responseLength;++i) {
                responseContent[i] = byteArrayList.get(i + 4);
            }

            // 检查校验和
            int check = 0;
            for (int i = 0; i < responseLength; ++i) {
                check = check + responseContent[i];
                if(check > 255) {
                    check = check % 256;
                }
            }
            check = (check + 256) % 256;
            check = (check + 256) % 256;
            if (check != checkSum) {
                System.out.println("[SocketTask]: {check/checkSum: "+check+" "+checkSum+"} data check error... ");
                byteArrayList.remove(0);
                break;
            }


            System.out.println("[SocketTask]: start handling data as order " + orderType);
            if (orderType == 1) {
                handleOrder1Response(responseContent);  // responseContent包括1位通信类型和n位网关号
            }
            else if (orderType == 2) {
                handleOrder2Response(responseContent);  // responseContent包括5位设备ID和1位成功失败标识
            }
            else if (orderType == 3) {
                handleOrder3and4Response(responseContent);
            }
            else if (orderType == 4) {
                handleOrder3and4Response(responseContent);
            }
            else if (orderType == 5) {
                handleOrder5Response(responseContent);
            }
            else if (orderType == 6) {
                handleOrder6Response(responseContent);
            }
            else if (orderType == 7) {
                handleOrder7Response(responseContent);
            }

            System.out.println("[SocketTask：socketInfoProcess]: 指令处理完成！");
            byteArrayList.remove(0);
            break;
        }
    }


    // 1位通信类型 + n位网关号 // 处理01指令的方法在这里用不到，因为01指令已经在NewLinkProcessor类处理了
    private void handleOrder1Response(byte[] responseContent) {
        if(responseContent.length == 0) return;
        int communicationMethod = responseContent[0];  // 通信类型
        //int netMaskID = byteArrayToInt(responseContent, 1, responseContent.length - 1);  // 网关ID
        // 将网关号所在的字节拷贝到字节数组charArrayNetmaskID上
        byte[] charArrayNetmaskID = new byte[responseContent.length - 1];
        System.arraycopy(responseContent, 1, charArrayNetmaskID, 0, charArrayNetmaskID.length);
        String netMaskID = byteArrayToString(charArrayNetmaskID,10);
    }
    // n位设备ID + 1位标识
    private void handleOrder2Response(byte[] responseContent) {
        if(responseContent.length == 0) return;
        int flag = byteToUnsignedValue(responseContent[responseContent.length - 1]);
        int deviceIDLength = byteToUnsignedValue(responseContent[0]);
        byte[] charArrayDeviceID = new byte[deviceIDLength];
        System.arraycopy(responseContent, 1, charArrayDeviceID, 0, deviceIDLength);
        String deviceID = byteArrayToString(charArrayDeviceID, 16).toUpperCase();

        String socketIp = socket.getInetAddress().getHostAddress();
        int socketPort = socket.getPort();
        String socketAddress = socketIp + ":" + socketPort;
        int netMaskId = ipNetmaskIDTable.get(socketAddress);

        if(flag % 16 == 0) return;

        System.out.println("[SocketTask:handleOrder2Response]: ");
        System.out.print("flag = " + flag + " deviceID = " + deviceID +
                " socketIp = " + socketIp + " socketPort = " + socketPort +
                " socketAddress =" + socketAddress + " netMaskId =" + netMaskId + "\n");

        if(flag == 17) {
            //返回删除成功
            socketTask.equipmentService.deleteEquipmentByDeviceID(deviceID);
        }
        else if(flag == 16) {
            //返回删除失败
        }
        else if(flag == 1) {
            //返回添加成功
            Equipment newEquipment = new Equipment();
            newEquipment.setEqpId(deviceID);
            newEquipment.setEqpName(deviceID); //设备名暂时设置为eqpId一样，等待网页后台添加设备部分获取用户输入自动修改
            //newEquipment.setObjectId(objectId);//NULL，暂时不设置，等待网页后台添加设备部分获取用户输入自动修改
            //newEquipment.setEqpType(eqpType);//NULL，暂时不设置，等待网页后台添加设备部分获取用户输入自动修改
            newEquipment.setSpecial(false); //该设备默认使用默认警报值而非特殊警报值，有需要单独去设置
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String registerDate = dateFormat.format(System.currentTimeMillis()).substring(0, 10);
            newEquipment.setRegisterDate(java.sql.Date.valueOf(registerDate));
            newEquipment.setNetmaskId(netMaskId);
            //newEquipment.setDeviceSerial(deviceSerialNew);
            int insertEquitmentResult = socketTask.equipmentService.insertEquipment(newEquipment); //
            if(insertEquitmentResult < 0) {
                //插入失败
                return;
            }
        }
        else if(flag == 0) {
            //返回添加失败
        }

    }
    // 1位ID长度（n）+n位设备ID +
    // 1位时间戳长度（m）+m位时间戳 +
    // 1位类型长度（q）+q位类型 +
    // 1位传感器数据长度（p）+p位传感器数据
    private void handleOrder3and4Response(byte[] responseContent) {
        if(responseContent.length == 0) return;

        int deviceIDLength = byteToUnsignedValue(responseContent[0]);
        int timestampLength = 4;
        int typeLength = byteToUnsignedValue(responseContent[1 + deviceIDLength + 1 + timestampLength + 1 - 1]);
        int sensorDataLength = byteToUnsignedValue(responseContent[1 + deviceIDLength + 1 + timestampLength + 1 + typeLength + 1 - 1]);

        byte[] byteArrayDeviceID = new byte[deviceIDLength];
        System.arraycopy(responseContent, 1, byteArrayDeviceID, 0, byteArrayDeviceID.length);
        String deviceID = byteArrayToString(byteArrayDeviceID, 16).toUpperCase();

        byte[] byteArrayTimestamp = new byte[timestampLength];
        System.arraycopy(responseContent, deviceIDLength+2, byteArrayTimestamp, 0, byteArrayTimestamp.length);
        String timestamp = byteArrayToString(byteArrayTimestamp, 10);
        SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmss"); //设置格式
        String timeinformat = format.format(Long.parseLong(timestamp + "000"));

        byte[] byteArrayType = new byte[typeLength];
        System.arraycopy(responseContent, deviceIDLength+timestampLength+3, byteArrayType, 0, byteArrayType.length);
        String typeBinaryString = toUnsignedBinaryString(byteArrayType);
        char[] typeBinaryCharArray = typeBinaryString.toCharArray();

        byte[] byteArraySensorData = new byte[sensorDataLength];
        System.arraycopy(responseContent, deviceIDLength+timestampLength+typeLength+4 , byteArraySensorData, 0, byteArraySensorData.length);

        String sensorType = deviceID.substring(5); //A000304

        System.out.println("[SocketTask:指令3&4]:deviceId/timestamp/type/sensorType:"+deviceID+" "+timestamp+" "+typeBinaryString + " " + sensorType);

        //连接InfluxDB
        influxDBConnector = new InfluxDBConnector("Andy","123456",
                "http://140.143.232.52:8086","health_data");
        influxDBConnector.connectToDatabase();
        influxDBConnector.setRetentionPolicy();
        Map<String, String> tags = new HashMap<>();
        Map<String, java.lang.Object> fields = new HashMap<>();

        String userId = "hitwh001";
        userId=socketTask.userNetmaskService.queryNetmaskRelatedUser(this.getTaskNum());
        String objectId = socketTask.userNetmaskService.queryObjectIdByUserId(userId,deviceID);
        System.out.println(userId+deviceID);





        int count1InType = 0;
        for(char c : typeBinaryString.toCharArray()) {
            if(c == '1') {
                count1InType = count1InType + 1;
            }
        }
        int[] sensorDataArray = new int[8 * typeLength];
        for(int i = 0; i < sensorDataArray.length; ++i) {
            sensorDataArray[i] = -1;
        }

        int low = 0;
        for(int i = 0;i < 8 * typeLength; ++i) {
            if(low+1 >= byteArraySensorData.length) break;

            System.out.println("typeBianryCharArray: " + typeBinaryCharArray[i]);
            if(low > byteArraySensorData.length){
//                System.out.println("[SocketTask:handleOrder3and4Response]: low > bytearraysensordata.length");
                break;
            }
            if(typeBinaryCharArray[i] == '1') {
                sensorDataArray[i] =byteToUnsignedValue (byteArraySensorData[low])*256 +
                        byteToUnsignedValue(byteArraySensorData[low+1]);
                System.out.println("传感器数据"+ i +":"+ low);
                System.out.println("传感器数据"+ i +":"+ byteArraySensorData[low]);
                System.out.println("传感器数据"+ i +":"+ sensorDataArray[i]);
                low = low + 2;
            }
        }
        System.out.print("[SocketTask:指令3&4]: byteArraySensorData: ");
        for(byte sd:byteArraySensorData) {
            System.out.print(byteToUnsignedValue(sd) + " ");
        }
        System.out.print("[SocketTask:指令3&4]: 传感器数据: ");
        for(int sd:sensorDataArray) {
            System.out.print(sd + " ");
        }
        System.out.println(" ");
        //床垫
        if(sensorType.equals("01")) {
            //System.out.println("床垫先跳过");


            ArrayList<Double> dataToInsert = new ArrayList<>();
            dataToInsert.add((double)sensorDataArray[6]);
            dataToInsert.add((double)sensorDataArray[2]);
            socketTask.dataService.insertData("Mattress", dataToInsert, this.taskNum,objectId,deviceID,timeinformat);
            System.out.println("SocketTask: 床垫数据已插入数据库, 数据采集时间" + timestamp);


            int special = socketTask.equipmentService.querySpecialValueByEqpId(deviceID);
            if (special == 0) {
                makePhoneMessageNormal("Mattress01",objectId,deviceID,dataToInsert);
            }
            else if(special == 1){
                makePhoneMessageSpecial("Mattress01",objectId,deviceID,dataToInsert);
            }


        }
        //血压
        else if(sensorType.equals("02")) {
//            tags.clear();
//            fields.clear();
//            tags.put("netmaskId", String.valueOf(this.getTaskNum()));
//            tags.put("eqpId", deviceID);
//            tags.put("objectId", objectId);
//            tags.put("sendTime",timestamp);
//            fields.put("heartRate", sensorDataArray[2]);
//            fields.put("highPressure", sensorDataArray[3]);
//            fields.put("lowPressure", sensorDataArray[4]);
//            influxDBConnector.insertData("bloodPressure", tags, fields);

            ArrayList<Double> dataToInsert = new ArrayList<>();
            dataToInsert.add((double)sensorDataArray[2]);
            dataToInsert.add((double)sensorDataArray[3]);
            dataToInsert.add((double)sensorDataArray[4]);
            socketTask.dataService.insertData("BloodPressure", dataToInsert, this.taskNum,objectId,deviceID,timeinformat);

            System.out.println("SocketTask: 血压数据已插入数据库, 数据采集时间" + timestamp);


            int special = socketTask.equipmentService.querySpecialValueByEqpId(deviceID);
            if (special == 0) {
                makePhoneMessageNormal("BloodPressure01",objectId,deviceID,dataToInsert);
            }
            else if(special == 1){
                makePhoneMessageSpecial("BloodPressure01",objectId,deviceID,dataToInsert);
            }

        }
        //血氧
        else if(sensorType.equals("03")) {
//            tags.clear();
//            fields.clear();
//            tags.put("netmaskId", String.valueOf(this.getTaskNum()));
//            tags.put("eqpId", deviceID);
//            tags.put("objectId", objectId);
//            tags.put("sendTime",timestamp);
//            fields.put("spo2", sensorDataArray[5]);
//            influxDBConnector.insertData("bloodOxygen", tags, fields);

            ArrayList<Double> dataToInsert = new ArrayList<>();
            dataToInsert.add((double)sensorDataArray[5]);
            socketTask.dataService.insertData("BloodOxygen", dataToInsert, this.taskNum,objectId,deviceID,timeinformat);
            System.out.println("SocketTask: 血氧数据已插入数据库, 数据采集时间" + timestamp);

            int special = socketTask.equipmentService.querySpecialValueByEqpId(deviceID);
            if (special == 0) {
                makePhoneMessageNormal("BloodOxygen01",objectId,deviceID,dataToInsert);
            }
            else if(special == 1){
                makePhoneMessageSpecial("BloodOxygen01",objectId,deviceID,dataToInsert);
            }

        }
        //温度
        else if(sensorType.equals("04")) {
//            tags.clear();
//            fields.clear();
//            tags.put("netmaskId", String.valueOf(this.getTaskNum()));
//            tags.put("eqpId", deviceID);
//            tags.put("objectId", objectId);
//            tags.put("sendTime", timeinformat);
//            fields.put("bodyTemp", sensorDataArray[1]/100);
//            fields.put("envTemp", sensorDataArray[0]/100);
//            influxDBConnector.insertData("temperature", tags, fields);

            ArrayList<Double> dataToInsert = new ArrayList<>();
            dataToInsert.add((double)sensorDataArray[1]/100);
            dataToInsert.add((double)sensorDataArray[0]/100);
            socketTask.dataService.insertData("Temperature", dataToInsert, this.taskNum,objectId,deviceID,timeinformat);
            System.out.println("SocketTask: 温度数据已插入数据库, 数据采集时间" + timestamp);

            int special = socketTask.equipmentService.querySpecialValueByEqpId(deviceID);
            if (special == 0) {
                makePhoneMessageNormal("Temperature01",objectId,deviceID,dataToInsert);
            }
            else if(special == 1){
                makePhoneMessageSpecial("Temperature01",objectId,deviceID,dataToInsert);
            }

            System.out.println("<SocketTask:> Temperature processure end!!!");

        }
        System.out.println("指令0304返回数据处理完成");
    }
    //暂时没用了
    private void handleOrder5Response(byte[] responseContent) {
        if(responseContent.length == 0) return;
        int flag = byteToUnsignedValue(responseContent[0]);

        if(flag == 0) {
            //改为Modbus

        }
        else {
            //改为AMQP

        }
    }
    //指令6：设备信息获取
    private void handleOrder6Response(byte[] responseContent) {
        if(responseContent.length == 0) return;
        int deviceIDLength = byteToUnsignedValue(responseContent[0]);
        int timestampLength = byteToUnsignedValue(responseContent[1 + deviceIDLength + 1 - 1]);

        byte[] byteArrayDeviceID = new byte[deviceIDLength];
        System.arraycopy(responseContent, 1, byteArrayDeviceID, 0, byteArrayDeviceID.length);
        String deviceID = byteArrayToString(byteArrayDeviceID, 16);
        byte[] byteArrayTimestamp = new byte[timestampLength];
        System.arraycopy(responseContent, deviceIDLength + 2, byteArrayTimestamp, 0, timestampLength);
        int timestamp = byteArrayToInt(byteArrayTimestamp, 0, byteArrayTimestamp.length - 1);

    }
    private void handleOrder7Response(byte[] responseContent) {
        if(responseContent.length == 0) return;
        int flag = byteToUnsignedValue(responseContent[responseContent.length - 1]);

        int deviceIDLength = byteToUnsignedValue(responseContent[0]);

        byte[] byteArrayDeviceID = new byte[deviceIDLength];
        System.arraycopy(responseContent, 1, byteArrayDeviceID, 0, byteArrayDeviceID.length);
        String deviceID = byteArrayToString(byteArrayDeviceID, 16);

        System.out.println("<SocketTask: handleOrder7Response>: deviceID/flag: " + deviceID + "/ " + flag);
        if(flag == 0) {
            //不存在
            System.out.println("<SocketTask: handleOrder7Response>"+deviceID+"不存在");
        }
        else if(flag == 1) {
            //成功
            System.out.println("<SocketTask: handleOrder7Response>"+deviceID+"成功");
        }
    }




    private void makePhoneMessageNormal(String dataType,String objectId,String eqpId,ArrayList<Double> data) {
        System.out.println("<<SocketTask:makePhoneMessageNormal>>");

        List<AlarmNormalValue> alarmNormalValueList = socketTask.alarmNormalValueService.queryAlarmNormalValueByEqpType(dataType);

        if (dataType.equals("BloodPressure01")) {
            if (data.size() != 3) return;
            if(data.get(0) != 255 && data.get(0) > alarmNormalValueList.get(4).getValue()) {
                String message = "心率"+String.valueOf(data.get(0))+"高于设定范围";
                sendPhoneMessage(objectId,eqpId,message,"心率",Float.parseFloat(data.get(0).toString()),"心率超出最高警戒值");
            }
            else if(data.get(0) != 0 && data.get(0) < alarmNormalValueList.get(5).getValue()){
                String message = "心率"+String.valueOf(data.get(0))+"低于设定范围";
                sendPhoneMessage(objectId,eqpId,message,"心率",Float.parseFloat(data.get(0).toString()),"心率低于最低警戒值");
            }
            else if(data.get(1) != 255 && data.get(1) > alarmNormalValueList.get(0).getValue()) {
                String message = "高压"+String.valueOf(data.get(1))+"高于设定范围";
                sendPhoneMessage(objectId,eqpId,message,"血压高压",Float.parseFloat(data.get(0).toString()),"血压高压超出最高警戒值");
            }
            else if(data.get(1) != 0 && data.get(1) < alarmNormalValueList.get(1).getValue()) {
                String message = "高压"+String.valueOf(data.get(1))+"低于设定范围";
                sendPhoneMessage(objectId,eqpId,message,"血压高压",Float.parseFloat(data.get(0).toString()),"血压高压低于最高警戒值");
            }
            else if(data.get(2) != 255 && data.get(2) > alarmNormalValueList.get(2).getValue()) {
                String message = "低压"+String.valueOf(data.get(2))+"高于设定范围";
                sendPhoneMessage(objectId,eqpId,message,"血压低压",Float.parseFloat(data.get(0).toString()),"血压低压超出最高警戒值");
            }
            else if(data.get(2) != 0 && data.get(2) < alarmNormalValueList.get(3).getValue()) {
                String message = "低压"+String.valueOf(data.get(2))+"低于设定范围";
                sendPhoneMessage(objectId,eqpId,message,"血压低压",Float.parseFloat(data.get(0).toString()),"血压低压低于最高警戒值");
            }
        } else if (dataType.equals("BloodOxygen01")) {
            if(data.size()!=1) return;
            if(data.get(0) < alarmNormalValueList.get(0).getValue()) {
                String message = "血氧" + String.valueOf(data.get(0)) + "低于正常范围";
                sendPhoneMessage(objectId,eqpId,message,"血氧",Float.parseFloat(data.get(0).toString()),"血氧低于正常范围");
            }
        } else if (dataType.equals("Temperature01")) {
            if(data.size()!=2) return;
            if(data.get(0) < 41.6 && data.get(0) > alarmNormalValueList.get(0).getValue()) {
                String message = "体温" + String.valueOf(data.get(0)) + "超出正常范围";
                sendPhoneMessage(objectId,eqpId,message,"体温",Float.parseFloat(data.get(0).toString()),"体温超出正常范围");
            }
            else if(data.get(0) > 30.0 && data.get(0) < alarmNormalValueList.get(1).getValue()) {
                String message = "体温" + String.valueOf(data.get(0)) + "低于正常范围";
                sendPhoneMessage(objectId,eqpId,message,"体温",Float.parseFloat(data.get(0).toString()),"体温低于正常范围");
            }
            else if(data.get(1) < 60 && data.get(1) > alarmNormalValueList.get(2).getValue()) {
                String message = "环境温度" + String.valueOf(data.get(0)) + "超出正常范围";
                sendPhoneMessage(objectId,eqpId,message,"环境温度",Float.parseFloat(data.get(1).toString()),"环境温度超出正常范围");
            }
            else if(data.get(1) > -50 && data.get(1) < alarmNormalValueList.get(3).getValue()) {
                String message = "环境温度" + String.valueOf(data.get(0)) + "低于正常范围";
                sendPhoneMessage(objectId,eqpId,message,"环境温度",Float.parseFloat(data.get(1).toString()),"环境温度低于正常范围");
            }
        } else if (dataType.equals("Mattress01")) {

        }
    }
    private void makePhoneMessageSpecial(String dataType,String objectId,String eqpId,ArrayList<Double> data) {
        System.out.println("<<SocketTask:makePhoneMessageSpecial>>");

        List<AlarmSpecialValue> alarmSpecialValueList = socketTask.alarmSpecialValueService.queryAlarmSpecialValueByEqpId(eqpId);

        if (dataType.equals("BloodPressure01")) {
            if (data.size() != 3) return;
            if(data.get(0) != 255 && data.get(0) > alarmSpecialValueList.get(4).getValue()) {
                String message = "心率"+String.valueOf(data.get(0))+"高于设定范围";
                sendPhoneMessage(objectId,eqpId,message,"心率",Float.parseFloat(data.get(0).toString()),"心率超出最高警戒值");
            }
            else if(data.get(0) != 0 && data.get(0) < alarmSpecialValueList.get(5).getValue()){
                String message = "心率"+String.valueOf(data.get(0))+"低于设定范围";
                sendPhoneMessage(objectId,eqpId,message,"心率",Float.parseFloat(data.get(0).toString()),"心率低于最低警戒值");
            }
            else if(data.get(1) != 255 && data.get(1) > alarmSpecialValueList.get(0).getValue()) {
                String message = "高压"+String.valueOf(data.get(1))+"高于设定范围";
                sendPhoneMessage(objectId,eqpId,message,"血压高压",Float.parseFloat(data.get(0).toString()),"血压高压超出最高警戒值");
            }
            else if(data.get(1) != 0 && data.get(1) < alarmSpecialValueList.get(1).getValue()) {
                String message = "高压"+String.valueOf(data.get(1))+"低于设定范围";
                sendPhoneMessage(objectId,eqpId,message,"血压高压",Float.parseFloat(data.get(0).toString()),"血压高压低于最高警戒值");
            }
            else if(data.get(2) != 255 && data.get(2) > alarmSpecialValueList.get(2).getValue()) {
                String message = "低压"+String.valueOf(data.get(2))+"高于设定范围";
                sendPhoneMessage(objectId,eqpId,message,"血压低压",Float.parseFloat(data.get(0).toString()),"血压低压超出最高警戒值");
            }
            else if(data.get(2) != 0 && data.get(2) < alarmSpecialValueList.get(3).getValue()) {
                String message = "低压"+String.valueOf(data.get(2))+"低于设定范围";
                sendPhoneMessage(objectId,eqpId,message,"血压低压",Float.parseFloat(data.get(0).toString()),"血压低压低于最高警戒值");
            }
        } else if (dataType.equals("BloodOxygen01")) {
            if(data.size()!=1) return;
            if(data.get(0) < alarmSpecialValueList.get(0).getValue()) {
                String message = "血氧" + String.valueOf(data.get(0)) + "低于正常范围";
                sendPhoneMessage(objectId,eqpId,message,"血氧",Float.parseFloat(data.get(0).toString()),"血氧低于正常范围");
            }
        } else if (dataType.equals("Temperature01")) {
            if(data.size()!=2) return;
            if(data.get(0) < 41.6 && data.get(0) > alarmSpecialValueList.get(0).getValue()) {
                String message = "体温" + String.valueOf(data.get(0)) + "超出正常范围";
                sendPhoneMessage(objectId,eqpId,message,"体温",Float.parseFloat(data.get(0).toString()),"体温超出正常范围");
            }
            else if(data.get(0) > 30.0 && data.get(0) < alarmSpecialValueList.get(1).getValue()) {
                String message = "体温" + String.valueOf(data.get(0)) + "低于正常范围";
                sendPhoneMessage(objectId,eqpId,message,"体温",Float.parseFloat(data.get(0).toString()),"体温低于正常范围");
            }
            else if(data.get(1) < 60 && data.get(1) > alarmSpecialValueList.get(2).getValue()) {
                String message = "环境温度" + String.valueOf(data.get(0)) + "超出正常范围";
                sendPhoneMessage(objectId,eqpId,message,"环境温度",Float.parseFloat(data.get(1).toString()),"环境温度超出正常范围");
            }
            else if(data.get(1) > -50 && data.get(1) < alarmSpecialValueList.get(3).getValue()) {
                String message = "环境温度" + String.valueOf(data.get(0)) + "低于正常范围";
                sendPhoneMessage(objectId,eqpId,message,"环境温度",Float.parseFloat(data.get(1).toString()),"环境温度低于正常范围");
            }
        } else if (dataType.equals("Mattress01")) {

        }
    }


    private void sendPhoneMessage(String objectId,String eqpId,String message,String alarmType,float alarmValue,String detail) {
        String date = dateformat.format(System.currentTimeMillis());
        String yearMonth = date.substring(0,7);//如2019-03
        AlarmLog alarmLog = new AlarmLog();

        int sendMessageCount = 1;

        com.humanhealthmonitor.pojo.Object object = socketTask.objectService.queryObjectByObjectId(objectId);
        String objectPhoneNumber = object.getObjectTel();
        System.out.println("SocketTask:sendPhoneMessage" + taskNum + ": objectPhoneNumber: " + objectPhoneNumber);
        String userId = object.getUserId();
        try {
            cloudMsgUtil.sendSingleCloudMsg(objectPhoneNumber,eqpId,message);
            if(!userId.equals(objectId)){
                String userPhoneNumber = socketTask.userService.queryUserByUserId(userId).getUserTel();
                cloudMsgUtil.sendSingleCloudMsg(userPhoneNumber,eqpId,message);
                sendMessageCount++; //说明发了两条短信
            }
        } catch (HTTPException | IOException e) {
            e.printStackTrace();
        }
        ObjectResourceUse objectResourceUse2 = socketTask.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(objectId,yearMonth);
        if(objectResourceUse2!=null) {
            objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+sendMessageCount);
            socketTask.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
        }


        //插入一条AlarmLog
        alarmLog.setObjectId(objectId);
        alarmLog.setEqpId(eqpId);
        alarmLog.setAlarmType(alarmType);
        alarmLog.setAlarmValue(alarmValue);
        String writeTime = dateformat.format(System.currentTimeMillis());
        alarmLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
        alarmLog.setDetail(detail);
        socketTask.alarmLogService.insertAlarmLog(alarmLog);
    }
}