package com.example.humanhealthmonitor;

import com.example.humanhealthmonitor.pojo.*;
import com.example.humanhealthmonitor.pojo.Object;
import com.example.humanhealthmonitor.service.*;
import com.github.qcloudsms.httpclient.HTTPException;
import org.apache.ibatis.javassist.bytecode.ByteArray;
import com.example.humanhealthmonitor.CloudMsgUtil;

import java.io.*;
//import java.lang.Object;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.humanhealthmonitor.HealthDataProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

import static com.example.humanhealthmonitor.MsgQueue.inetAddressArray;
import static com.example.humanhealthmonitor.MsgQueue.protocolState;
import static com.example.humanhealthmonitor.MsgQueue.sendMsgQueue;

/**
 * 用来处理Socket请求
 */
//@Controller
@Component///////
public class SocketTask implements Runnable {

    @Autowired
    private EquipmentService equipmentService;/////////added0521
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

    private static SocketTask socketTask;////////added0521//静态私有化变量，所有类共享一份

    private int taskNum = 0;//任务号初始化为0//added0523

    private InfluxDBConnector influxDBConnector;//创建influxDB连接实例

    private CloudMsgUtil cloudMsgUtil = new CloudMsgUtil();//云短信工具

    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Socket socket;

    public int getTaskNum()
    {
        return this.taskNum;
    }
    public void setTaskNum(int taskNum)
    {
        this.taskNum = taskNum;
    }

//    SocketTask(Socket socket) {
//        this.socket = socket;
//    }//comment0521
    public void setSocket(Socket socket){//added0521
        this.socket = socket;
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
    }
    public void run() {
        try {
//            socket = HumanhealthmonitorApplication.serverSocket.accept();////////////////
            handleSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 与客户端Socket进行通信
    private void handleSocket() throws Exception {
//        HealthDataProcessor healthDataProcessor = new HealthDataProcessor();//实例化信息处理类
        //如果收到了信息就把信息打印出来
        System.out.println("SocketTask: taskNum: "+this.getTaskNum());

        protocolState[taskNum-1] = 1;//修改协议状态为MODBUS，表明网关已经连接且使用MODBUS//added0526
        System.out.println("SocketTask: change protocolState of netMask"+taskNum + " to 1(MODBUS)...");

        PrintWriter pw = null;
        System.out.println("SocketTask: "+socket.getInetAddress() + " transferred to socketTask...");
        pw = new PrintWriter(socket.getOutputStream());
//        pw.println("FEFE0401040005AABB");
//        pw.flush();
//        String orderString = "FEFE0401040005AABB";
        //询问网关上设备号和采集数组下标对应关系
        //组装查询命令
        String startStr = "FEFE04";
        String netMaskIdStr = String.valueOf(taskNum);
        if (netMaskIdStr.length()==1)
        {
            netMaskIdStr = "0"+netMaskIdStr;
        }
        String orderType = "06";
        int checkCal = 5 + taskNum;
        checkCal = Math.abs(checkCal)%64;//计算校验和
        String checkCalStr = Integer.toHexString(checkCal).toUpperCase();
        if(checkCalStr.length()==1)
        {
            checkCalStr = "0"+checkCalStr;
        }
        String findSerialOrder = startStr + netMaskIdStr + orderType + "00" + checkCalStr + "AABB";
        System.out.println("SocketTask"+taskNum+": send findSerialOrder: "+findSerialOrder);
        sendMsgQueue.get(taskNum-1).offer(findSerialOrder);

        while (sendMsgQueue.get(taskNum-1).size()==0)//为空则线程休眠//modified0524
        {
            Thread.sleep(1000);//1秒
        }
//        String orderString = sendMsgQueue[taskNum-1].poll();//取出一条命令//modified0524
        String orderString = sendMsgQueue.get(taskNum-1).poll();//modified0524

        byte[] orderByte = toByteArray(orderString);

//        byte[] orderByte = orderString.getBytes();
        OutputStream os = socket.getOutputStream();
        os.write(orderByte);
        os.flush(); // ***********
        System.out.println("SocketTask"+taskNum+": send: " + bytesToHexString(orderByte));
        //字节读取
        // 装饰流BufferedReader封装输入流（接收客户端的流）
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        DataInputStream dis = new DataInputStream(bis);
        byte[] bytes = new byte[1]; // 一次读取一个byte
        String info = "";
        List<Byte> byteArrayList = new ArrayList<>();//字节列表

        while (dis.read(bytes) != -1) {//如何实现循环接收的呢？忘了。。
            info += bytesToHexString(bytes) + " ";//转为16进制字符串
            byteArrayList.add(bytes[0]);//字节列表
            if (dis.available() == 0) { //客户端一条信息结束
                System.out.println("SocketTask"+taskNum+": received: " + info);
                System.out.println("SocketTask"+taskNum+": byteArrayList: " + byteArrayList);
                socketInfoProcess(byteArrayList);

                //线程休眠
//                Thread thread = new Thread(this);
//                Thread.sleep(10000);

                info = "";//将info清空
                byteArrayList.clear();//字节列表清空
//                orderString = "FEFE0401040005AABB";
                while (sendMsgQueue.get(taskNum-1).isEmpty())//modified0524
                {
                    Thread.sleep(1000);
                }
                orderString = sendMsgQueue.get(taskNum-1).poll();//modified0524
                orderByte = toByteArray(orderString);
//                pw.println(orderString);
//                pw.write(orderByte);
//                pw.println(orderByte);
//                pw.flush();
                os.write(orderByte);
                os.flush();
                System.out.println("SocketTask"+taskNum+": send: " + bytesToHexString(orderByte));
            }
        }

        //added0525
        String inetAddressStr = socket.getInetAddress().toString();
        Iterator iterator = inetAddressArray.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(inetAddressStr)) {
                iterator.remove();
                break;
            }
        }


        socket.shutdownInput();//added 2019/04/08关闭输入流
        socket.shutdownOutput();//added 2019/04/10关闭输出流

        //关闭相对应的资源
        pw.close();
        bis.close();
        dis.close();
        socket.close();
    }

    //字节转为16进制字符串，如“FE”
    public String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public byte[] toByteArray(String hexString) {
        if (hexString.equals("")) {
            System.out.println("SocketTask"+taskNum+": toByteArray(): this hexString is empty");
            throw new IllegalArgumentException("this hexString must not be empty");
        }
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    //将1个字节的8个位解析成无符号0-255的值
    public int byteToUnsignedValue(Byte b) {
        int bInt = (int) b;
        if (bInt >= 0) {
            return bInt;
        } else {
            return (bInt + 256);
        }
    }

    //int转为两位16进制字符串
    public String byteToHexStringSocketTask(Byte b)
    {
        int bInt = byteToUnsignedValue(b);
        String str = Integer.toHexString(bInt);
        if(str.length()==1)
        {
            str = "0" + str;
        }
        return str;
    }

    public int byteArrayToInt (byte[] byteArray, int start, int end) {
        if(byteArray == null || byteArray.length == 0 || start > end || start < 0 || end >= byteArray.length) return -1;
        int res = 0;
        byte[] a = new byte[4];
        int i = a.length - 1, j = byteArray.length - 1;
        for (; i >= 0; --i, --j) {
            if(j >= 0)
                a[i] = byteArray[j];
            else
                a[i] = 0;
        }
        int v0 = (a[0] & 0xff) << 24;
        int v1 = (a[1] & 0xff) << 16;
        int v2 = (a[2] & 0xff) << 8;
        int v3 = (a[3] & 0xff) << 0;

        return v0 + v1 + v2 + v3;
    }

    public String byteArrayToString (byte[] byteArray, int radix, int start, int end) {

    }

    public void handleOrder1Response(byte[] responseContent) {
        int communicationMethod = responseContent[0];  // 通信类型
        //int netMaskID = byteArrayToInt(responseContent, 1, responseContent.length - 1);  // 网关ID
        char[] 
        String netMaskID = byteArrayToString(responseContent,10 , 1, responseContent.length - 1);
    }
    public void handleOrder2Response(byte[] responseContent) {

    }
    public void handleOrder3Response(byte[] responseContent) {

    }
    public void handleOrder4Response(byte[] responseContent) {

    }
    public void handleOrder5Response(byte[] responseContent) {

    }
    public void handleOrder6Response(byte[] responseContent) {

    }
    public void handleOrder7Response(byte[] responseContent) {

    }

    //处理Socket收到的信息
    public void socketInfoProcess(List<Byte> byteArrayList) {
        String date = dateformat.format(System.currentTimeMillis());
        String yearMonth = date.substring(0,7);//如2019-03

        Equipment equipmentData;//added0521
        //从网关
        // 发来的只带一个字节有效数据的帧长度就是10，比这个小的就是坏掉的或无关的
        while (byteArrayList.size() >= 8) {
            int orderType = byteToUnsignedValue(byteArrayList.get(2)); // 指令码
            int responseLength = byteToUnsignedValue(byteArrayList.get(3)); // 回复内容长度
            byte[] responseContent = new byte[responseLength];
            int checkSum = byteToUnsignedValue(byteArrayList.get(byteArrayList.size()-3)); // 校验和

            if (byteArrayList.get(0) != (byte) 0xFE || byteArrayList.get(1) != (byte) 0xFE) {
                break;
            }
            if (byteArrayList.size() != responseLength + 5) {
                break;
            }
            if (byteArrayList.get(byteArrayList.size() - 2) != (byte) 0xAA || byteArrayList.get(byteArrayList.size() - 1) != (byte) 0xBB) {
                break;
            }


            // 将回复信息放到responseContent
            for (int i = 0; i < responseLength;++i) {
                responseContent[i] = byteArrayList.get(i + 3);
            }

            if (orderType == 1) {
                handleOrder1Response(responseContent);
            }
            else if (orderType == 2) {
                handleOrder2Response(responseContent);
            }
            else if (orderType == 3) {
                handleOrder3Response(responseContent);
            }
            else if (orderType == 4) {
                handleOrder4Response(responseContent);
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

            if (byteArrayList.size() >= (dataLength + 6 + 3))//字节总长度达不到，证明数据损坏，这里的9是数据前6后3附加字节总长
            {
                if (byteArrayList.get(dataLength + 9 - 2) == (byte) 0xAA && byteArrayList.get(dataLength + 9 - 1) == (byte) 0xBB)//验证结尾格式AABB
                {
                    //校验和计算
                    int check = 0;
                    for (int i = 0; i < dataLength; i++) {
                        check += byteArrayList.get(i + 6);
                    }
                    check = Math.abs(check) % 64;
                    if (check == byteArrayList.get(dataLength + 9 - 3))//比对数据发送前后的校验和，一致则继续，不一致说明数据传输错误//这里需要判断包长会否大于大dataLength+9-3，防止出错
                    {
                        System.out.println("SocketTask"+taskNum+": check pass...");
                        int netMaskId = byteToUnsignedValue(byteArrayList.get(2));//这里要注意，网关id是按0x11字面16转10进制的值17来与上面对应

                        //修改或维持发来消息的网关状态为MODBUS
                        if(protocolState[netMaskId-1] != 1)
                        {
                            protocolState[netMaskId-1] = 1;
                            System.out.println("SocketTask"+taskNum+": change protocolState of netMask "+netMaskId + " to 1(MODBUS)...");
                        }

                        System.out.println("SocketTask"+taskNum+": netMaskId: " + netMaskId + "  orderType: " + orderType);
                        List<Byte> dataList = byteArrayList.subList(6, 6 + dataLength);//取出校验成功的数据区数据，放到dataList中，数据长度为datalength而不是datalength+1

                        if(orderType == 6)//设备号与网关顺序号对应关系修正
                        {
                            int deviceNum = dataList.size()/6;
                            List<Byte> miniDataList = new ArrayList<>();
                            for (int j = 0;j < deviceNum;j++)
                            {
                                miniDataList = dataList.subList(6*j,6*j+6);//不包括最后一个值
                                int deviceSerialNew = byteToUnsignedValue(miniDataList.get(5));//顺序号
                                String eqpId = "";
                                for(int i = 0;i < 5;i++)
                                {
                                    eqpId += byteToHexStringSocketTask(miniDataList.get(i));
                                }
                                eqpId = eqpId.substring(1,10).toUpperCase();//左闭右开，不包括10位置，一共10-1字符
                                System.out.println("SocketTask"+taskNum+": order06 eqpId: "+eqpId);

                                Equipment equipment1 = socketTask.equipmentService.queryEquipmentByEqpId(eqpId);
                                if (equipment1 == null)
                                {
                                    System.out.println("SocketTask"+taskNum+": order06 equipment1 null");
                                }else{
                                    System.out.println("SocketTask"+taskNum+": order06 equipment1 not null");
                                    int deviceNetMaskId = equipment1.getNetmaskId();
                                    if(deviceNetMaskId != netMaskId)
                                    {
                                        equipment1.setNetmaskId(netMaskId);
                                        socketTask.equipmentService.updateEquipmentNetMaskId(equipment1);
                                        System.out.println("SocketTask"+taskNum+": order06 a netMaskId updated,,,deviceNum="+deviceNum);
                                    }
                                    int deviceSerialOrigin = equipment1.getDeviceSerial();
                                    if (deviceSerialOrigin != deviceSerialNew)
                                    {
                                        equipment1.setDeviceSerial(deviceSerialNew);
                                        socketTask.equipmentService.updateEquipmentDeviceSerial(equipment1);
                                        System.out.println("SocketTask"+taskNum+": order06 a deviceSerial updated,,,deviceNum="+deviceNum);
                                    }
                                }

                            }
                            break;//退出while循环，此方法结束
                        }

                        if(orderType == 2)//云平台操作添加设备的回传消息，有这个消息说明网关查找设备成功//6字节
                        {
                            int deviceSerialNew = byteToUnsignedValue(dataList.get(5));//顺序号

                            // yuan: deviceSerialNew = 0表示网关查找设备失败
                            if(deviceSerialNew == 0) {
                                // 失败后的处理

                            }
                            else {
                                String eqpId = "";
                                for(int i = 0;i < 5;i++)
                                {
                                    eqpId = eqpId + byteToHexStringSocketTask(dataList.get(i));
                                }
                                eqpId = eqpId.substring(1,10).toUpperCase();//左闭右开，不包括10位置，一共10-1字符
                                System.out.println("SocketTask"+taskNum+": eqpId add success: "+eqpId);

                                //这块转移到接收解析那块
                                Equipment newEquipment = new Equipment();
                                newEquipment.setEqpId(eqpId);
                                newEquipment.setEqpName(eqpId);//设备名暂时设置为eqpId一样，等待网页后台添加设备部分获取用户输入自动修改
                                // newEquipment.setObjectId(objectId);//NULL，暂时不设置，等待网页后台添加设备部分获取用户输入自动修改
                                // newEquipment.setEqpType(eqpType);//NULL，暂时不设置，等待网页后台添加设备部分获取用户输入自动修改
                                newEquipment.setSpecial(false);//该设备默认使用默认警报值而非特殊警报值，有需要单独去设置
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String registerDate = dateFormat.format(System.currentTimeMillis()).substring(0, 10);
                                newEquipment.setRegisterDate(java.sql.Date.valueOf(registerDate));
                                newEquipment.setNetmaskId(netMaskId);
                                newEquipment.setDeviceSerial(deviceSerialNew);
                                socketTask.equipmentService.insertEquipment(newEquipment);

                            }

                            break;//退出while循环，此方法结束
                        }
                        int validLength = 0;//存储有效数据长度
                        //存储时间
                        byte[] timeByte = new byte[7];
                        String timeString = "";

                        //连接InfluxDB
                        influxDBConnector = new InfluxDBConnector("Andy","123456",
                                "http://140.143.232.52:8086","health_data");
                        influxDBConnector.connectToDatabase();
                        influxDBConnector.setRetentionPolicy();
                        Map<String, String> tags = new HashMap<>();
                        Map<String, java.lang.Object> fields = new HashMap<>();

                        //传感器数据解析
                        while (dataList.size() >= 13) {
                            int deviceSerial = byteToUnsignedValue(dataList.get(0));//数据对应的在网关数据区的序号
                            validLength = byteToUnsignedValue(dataList.get(1));//获取有效数据长度
                            System.out.println("SocketTask"+taskNum+": deviceSerial: " + deviceSerial + "  validLength: " + validLength);
                            //根据deviceSerial查询数据库，获知设备类型，根据设备类型case解码
                            equipmentData = socketTask.equipmentService .queryEquipmentByNetSerial(netMaskId,deviceSerial);/////////added0521/////////
                            int typeNum = -1;
                            if (equipmentData != null)
                            {
                                System.out.println("SocketTask"+taskNum+": EqpId: "+equipmentData.getEqpId());////////////////////////added0521
                                //更新设备对应的object的dataCount数据加1
                                ObjectResourceUse objectResourceUse = socketTask.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                if(objectResourceUse != null)
                                {
                                    objectResourceUse.setDataCount(objectResourceUse.getDataCount()+1);
                                    socketTask.objectResouceUseService.updateObjectResourceUseOnlyDataCount(objectResourceUse);
                                }else {
                                    String year = date.substring(0,4);
                                    String month = date.substring(5,7);
                                    ObjectResourceUse newObjectResourceUse = new ObjectResourceUse();
                                    newObjectResourceUse.setObjectId(equipmentData.getObjectId());
                                    newObjectResourceUse.setYearMonth(yearMonth);
                                    java.sql.Date beginDate=java.sql.Date.valueOf(yearMonth+"-01");
                                    newObjectResourceUse.setBeginDate(beginDate);
                                    java.sql.Date endDate = beginDate;
                                    if(month.equals("01")||month.equals("03")||month.equals("05")||month.equals("07")||
                                            month.equals("08")||month.equals("10")||month.equals("12"))
                                    {
                                        endDate = java.sql.Date.valueOf(yearMonth+"-31");
                                    }else if(month.equals("04")||month.equals("06")||month.equals("09")||month.equals("11"))
                                    {
                                        endDate = java.sql.Date.valueOf(yearMonth+"-30");
                                    }else if(month.equals("02"))
                                    {
                                        if(Integer.valueOf(year)%100 == 0)
                                        {
                                            if (Integer.valueOf(year)%400 == 0)
                                            {
                                                endDate = java.sql.Date.valueOf(yearMonth+"-29");
                                            }
                                            else
                                            {
                                                endDate = java.sql.Date.valueOf(yearMonth+"-28");
                                            }
                                        }else {
                                            if (Integer.valueOf(year)%4 == 0)
                                            {
                                                endDate = java.sql.Date.valueOf(yearMonth+"-29");
                                            }else {
                                                endDate = java.sql.Date.valueOf(yearMonth+"-28");
                                            }
                                        }
                                    }else
                                    {
                                        System.out.println("SocketTask: resourceUse endDate month error...");
                                    }
                                    newObjectResourceUse.setEndDate(endDate);
                                    newObjectResourceUse.setMsgCount(0);
                                    newObjectResourceUse.setOnlineTimeLength(0);
                                    newObjectResourceUse.setDataCount(0);
                                    objectResouceUseService.insertObjectResourceUse(newObjectResourceUse);

                                    newObjectResourceUse.setDataCount(1);
                                    socketTask.objectResouceUseService.updateObjectResourceUseOnlyDataCount(newObjectResourceUse);
                                }


                                //判断设备类型
                                if (equipmentData.getEqpType().equals("BloodPressure01"))
                                {
                                    typeNum = 0;
                                }else if (equipmentData.getEqpType().equals("Temperature01"))
                                {
                                    typeNum = 1;
                                }else if (equipmentData.getEqpType().equals("BloodOxygen01"))
                                {
                                    typeNum = 2;
                                }
                            }

                            switch (typeNum) {
                                case 0:
                                    int highBloodPressure = byteToUnsignedValue(dataList.get(2));
                                    int lowBloodPressure = byteToUnsignedValue(dataList.get(3));
                                    int heartRate = byteToUnsignedValue(dataList.get(4));
                                    for (int i = 0; i < 7; i++) {
                                        timeByte[i] = dataList.get(i + 5);
                                    }
                                    timeString = bytesToHexString(timeByte);
                                    System.out.println("SocketTask"+taskNum+": BloodPressure... " + "highBloodPressure: " + highBloodPressure + "  lowBloodPressure: " +
                                            lowBloodPressure + "  heartBeat: " + heartRate + " bloodPressureTimeString: " + timeString);

                                    //数据过滤
                                    if (highBloodPressure == 255 && lowBloodPressure == 255 && heartRate==255)
                                    {
                                        System.out.println("SocketTask"+taskNum+": invalid BloodPressure01 255 data...");
                                    }else{
//                                            equipmentData = socketTask.equipmentService .queryEquipmentByNetSerial(netMaskId,deviceSerial);/////////added0521/////////
//                                            System.out.println("SocketTask"+taskNum+": EqpId: "+equipmentData.getEqpId());////////////////////////added0521

                                        //插入新数据到influxDB
                                        tags.clear();
                                        fields.clear();
                                        tags.put("netmaskId", String.valueOf(netMaskId));
                                        tags.put("eqpId", equipmentData.getEqpId());
                                        tags.put("objectId",equipmentData.getObjectId());
                                        tags.put("sendTime",timeString);
//                                            java.lang.Object highBloodPressureObj = highBloodPressure;////
                                        fields.put("highPressure", highBloodPressure);////
//                                            java.lang.Object lowBloodPressureObj = lowBloodPressure;////
                                        fields.put("lowPressure", lowBloodPressure);////
                                        fields.put("heartRate",heartRate);
                                        influxDBConnector.insertData("bloodPressure", tags, fields);


                                    }
                                    break;
                                case 1:
                                    int bodyTemperatureInt = byteToUnsignedValue(dataList.get(2)) * 256 + byteToUnsignedValue(dataList.get(3));
                                    float bodyTemperature = (float) bodyTemperatureInt / 100;
                                    int envTemperatureInt = byteToUnsignedValue(dataList.get(4)) * 256 + byteToUnsignedValue(dataList.get(5));
                                    float envTemperature = (float) envTemperatureInt / 100;
                                    for (int i = 0; i < 7; i++) {
                                        timeByte[i] = dataList.get(i + 6);
                                    }
                                    timeString = bytesToHexString(timeByte);
                                    System.out.println("SocketTask"+taskNum+": Temperature... " + "bodyTemperature: " + bodyTemperature + "  envTemperature: " +
                                            envTemperature + " temperatureTimeString: " + timeString);
                                    //数据过滤
                                    if (bodyTemperature == 0 && envTemperature == 0)
                                    {
                                        System.out.println("SocketTask"+taskNum+": invalid Temperature01 0 data...");
                                    }else{
                                        equipmentData = socketTask.equipmentService .queryEquipmentByNetSerial(netMaskId,deviceSerial);/////////added0521/////////
                                        System.out.println("SocketTask"+taskNum+": EqpId: "+equipmentData.getEqpId());////////////////////////added0521

                                        //插入新数据到influxDB
                                        tags.clear();
                                        fields.clear();
                                        tags.put("netmaskId", String.valueOf(netMaskId));
                                        tags.put("eqpId", equipmentData.getEqpId());
                                        tags.put("objectId",equipmentData.getObjectId());
//                                        tags.put("eqpId", "A00030101");
//                                        tags.put("objectId","hitwhob001");
                                        tags.put("sendTime",timeString);
                                        fields.put("bodyTemp",bodyTemperature);
                                        fields.put("envTemp",envTemperature);
                                        influxDBConnector.insertData("temperature", tags, fields);

                                    }
                                    break;
                                case 2:
                                    int bloodOxygenDegree = byteToUnsignedValue(dataList.get(2));
                                    for (int i = 0; i < 7; i++) {
                                        timeByte[i] = dataList.get(i + 3);
                                    }
                                    timeString = bytesToHexString(timeByte);
                                    System.out.println("SocketTask"+taskNum+": BloodOxygen... " + "bodyOxygenDegree: " + bloodOxygenDegree + " timeString: " +
                                            timeString);
                                    //数据过滤
                                    if (bloodOxygenDegree == 0)
                                    {
                                        System.out.println("SocketTask"+taskNum+": invalid BloodOxygen01 0 data...");
                                    }else {
                                        equipmentData = socketTask.equipmentService .queryEquipmentByNetSerial(netMaskId,deviceSerial);/////////added0521/////////
                                        System.out.println("SocketTask"+taskNum+": EqpId: "+equipmentData.getEqpId());////////////////////////added0521
                                        //插入新数据到influxDB
                                        tags.clear();
                                        fields.clear();
                                        tags.put("netmaskId", String.valueOf(netMaskId));
                                        tags.put("eqpId", equipmentData.getEqpId());
                                        tags.put("objectId",equipmentData.getObjectId());
//                                        tags.put("eqpId", "A00060302");
//                                        tags.put("objectId","hitwhob001");
                                        tags.put("sendTime",timeString);
                                        fields.put("spo2",bloodOxygenDegree);
                                        influxDBConnector.insertData("bloodOxygen", tags, fields);

                                    }

                                    break;
                                default:
                                    System.out.println("SocketTask"+taskNum+": Exception: unknown device type...");
                                    break;
                            }
                            dataList = dataList.subList(13, dataList.size());//去掉已经处理的13个字节，进入下一个循环
                        }
                        //内圈while处理完之后
                        System.out.println("SocketTask"+taskNum+": Info: data section analyze completed...");
                        //可能存在dataLength写的值大于实际值，出现dataLength + 9 < byteArrayList.size()的情况，于是加了一个判断来避免异常
                        if(dataLength + 9 < byteArrayList.size())//如果小于，则截取然后继续，针对的是一个数据包内多个回复帧的情况
                        {
                            byteArrayList = byteArrayList.subList(dataLength + 9, byteArrayList.size());
                        }
                        else//如果数据分析完毕，直接跳出外圈while循环
                        {
                            System.out.println("SocketTask"+taskNum+": Info: package analyze completed....");
                            break;
                        }
                        //校验和验证，验证成功继续，然后是判断设备类型，这里先简易地写死，00是血压，01是温度。02是血氧，
                        //然后把数据库里面的设备号改成网关一样的，或者设备上加字段，设备号之外再加网关和网关内设备序号
                        //取出数据后存入influxdb

                    } else {//校验和错误，去掉最前面的一个字节，进入下一个循环
                        System.out.println("SocketTask"+taskNum+": data check error...");
                        byteArrayList.remove(0);
                    }
                } else {//结尾格式不是AABB，证明数据损坏，去掉最前面的一个字节，进入下一个循环
                    System.out.println("SocketTask"+taskNum+": package tail is broken...");
                    byteArrayList.remove(0);
                }

            } else {//字节总长度达不到，证明数据可能损坏，去掉最前面的一个字节，进入下一个循环
                System.out.println("SocketTask"+taskNum+": package length error compared to dataLength, maybe package is broken...");
                byteArrayList.remove(0);
//                    break;
            }

        }
    }


}
