package com.example.humanhealthmonitor;

import com.example.humanhealthmonitor.pojo.*;
import com.example.humanhealthmonitor.service.*;
import com.github.qcloudsms.httpclient.HTTPException;
import org.apache.ibatis.javassist.bytecode.ByteArray;
import com.example.humanhealthmonitor.CloudMsgUtil;

import java.io.*;
//import java.lang.Object;
import java.math.BigInteger;
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
@Component
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

    //SocketTask(Socket socket) {
    //    this.socket = socket;
    //}//comment0521
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
        //socket = HumanhealthmonitorApplication.serverSocket.accept();////////////////
            handleSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //与客户端Socket进行通信
    private void handleSocket() throws Exception {
        //HealthDataProcessor healthDataProcessor = new HealthDataProcessor();//实例化信息处理类
        //如果收到了信息就把信息打印出来
        System.out.println("SocketTask: taskNum: "+this.getTaskNum());

        protocolState[taskNum-1] = 1;//修改协议状态为MODBUS，表明网关已经连接且使用MODBUS//added0526
        System.out.println("SocketTask: change protocolState of netMask"+taskNum + " to 1(MODBUS)...");

        PrintWriter pw = null;
        System.out.println("SocketTask: "+socket.getInetAddress() + " transferred to socketTask...");
        pw = new PrintWriter(socket.getOutputStream());
        //pw.println("FEFE0401040005AABB");
        //pw.flush();
        //String orderString = "FEFE0401040005AABB";
        //询问网关上设备号和采集数组下标对应关系
        //组装查询命令
        String startStr = "FEFE04";
        String netMaskIdStr = String.valueOf(taskNum);
        if (netMaskIdStr.length()==1) {
            netMaskIdStr = "0"+netMaskIdStr;
        }
        String orderType = "06";
        int checkCal = 5 + taskNum;
        checkCal = Math.abs(checkCal)%64;//计算校验和
        String checkCalStr = Integer.toHexString(checkCal).toUpperCase();
        if(checkCalStr.length()==1) {
            checkCalStr = "0"+checkCalStr;
        }
        String findSerialOrder = startStr + netMaskIdStr + orderType + "00" + checkCalStr + "AABB";
        System.out.println("SocketTask"+taskNum+": send findSerialOrder: "+findSerialOrder);
        sendMsgQueue.get(taskNum-1).offer(findSerialOrder);

        while (sendMsgQueue.get(taskNum-1).size()==0) {//为空则线程休眠//modified0524
            Thread.sleep(1000);//1秒
        }
        //String orderString = sendMsgQueue[taskNum-1].poll();//取出一条命令//modified0524
        String orderString = sendMsgQueue.get(taskNum-1).poll();//modified0524

        byte[] orderByte = toByteArray(orderString);

        //byte[] orderByte = orderString.getBytes();
        OutputStream os = socket.getOutputStream();
        os.write(orderByte);
        os.flush(); // ***********
        System.out.println("SocketTask"+taskNum+": send: " + bytesToHexString(orderByte));
        //字节读取
        //装饰流BufferedReader封装输入流（接收客户端的流）
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
                //Thread thread = new Thread(this);
                //Thread.sleep(10000);

                info = "";//将info清空
                byteArrayList.clear();//字节列表清空
                //orderString = "FEFE0401040005AABB";
                while (sendMsgQueue.get(taskNum-1).isEmpty()) {//modified0524
                    Thread.sleep(1000);
                }
                orderString = sendMsgQueue.get(taskNum-1).poll();//modified0524
                orderByte = toByteArray(orderString);
                //pw.println(orderString);
                //pw.write(orderByte);
                //pw.println(orderByte);
                //w.flush();
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
    public String byteToHexStringSocketTask(Byte b) {
        int bInt = byteToUnsignedValue(b);
        String str = Integer.toHexString(bInt);
        if(str.length()==1) {
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

    /**
     * 将byte[]转为各种进制的字符串
     * @param radix 基数可以转换进制的范围(2-36)，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
     * @return 转换后的字符串
     */
    public String byteArrayToString (byte[] byteArray, int radix) {
        return new BigInteger(1, byteArray).toString(radix);
    }


    // 人体红外线传感器： 2字节环境温度 + 2字节体温
    public void processDataType1(byte[] byteArrayData) {
        double ambientTemp ,bodyTemp;


    }
    // 血压设备： 1字节心率 + 1字节收缩压(systolic pressure) + 1字节舒张压(diastolic pressure)
    public void processDataType2(byte[] byteArrayData) {
        int heartRate = byteToUnsignedValue(byteArrayData[0]);
        int systolicPressure = byteToUnsignedValue(byteArrayData[1]);
        int diastolicPressure = byteToUnsignedValue(byteArrayData[2]);

    }
    // 血氧设备： 血氧饱和度(简写SpO2)
    public void processDataType3(byte[] byteArrayData) {
        double SpO2;

    }
    // 床垫： 2字节心跳 + 2字节呼吸 + 2字节温度 + 1字节动作
    public void processDataType4(byte[] byteArrayData){
        int heartRate, breathFrequency, temp, action;

    }


    // 1位通信类型 + n位网关号
    public void handleOrder1Response(byte[] responseContent) {
        int communicationMethod = responseContent[0];  // 通信类型
        //int netMaskID = byteArrayToInt(responseContent, 1, responseContent.length - 1);  // 网关ID

        // 将网关号所在的字节拷贝到字节数组charArrayNetmaskID上
        byte[] charArrayNetmaskID = new byte[responseContent.length - 1];
        System.arraycopy(responseContent, 1, charArrayNetmaskID, 0, charArrayNetmaskID.length);
        String netMaskID = byteArrayToString(charArrayNetmaskID,10);

    }
    // n位设备ID + 1位标识
    public void handleOrder2Response(byte[] responseContent) {
        int flag = byteToUnsignedValue(responseContent[responseContent.length - 1]);
        byte[] charArrayDeviceID = new byte[responseContent.length - 1];
        System.arraycopy(responseContent, 0, charArrayDeviceID, 0, charArrayDeviceID.length);
        String deviceID = byteArrayToString(charArrayDeviceID, 16);

    }
    // 1位ID长度（n） + n位设备ID + 1位时间戳长度（m） + m位时间戳 + 1位传感器数据长度（p） + p位传感器数据
    public void handleOrder3Response(byte[] responseContent) {
        int deviceIDLength = byteToUnsignedValue(responseContent[0]);
        int timestampLength = 4;
        int sensorDataLength = byteToUnsignedValue(responseContent[1 + deviceIDLength + 1 + timestampLength + 1 - 1]);

        byte[] byteArrayDeviceID = new byte[deviceIDLength];
        System.arraycopy(responseContent, 1, byteArrayDeviceID, 0, byteArrayDeviceID.length);
        String deviceID = byteArrayToString(byteArrayDeviceID, 16);

        byte[] byteArrayTimestamp = new byte[timestampLength];
        System.arraycopy(responseContent, deviceIDLength + 2, byteArrayTimestamp, 0, byteArrayTimestamp.length);
        String timestamp = byteArrayToString(byteArrayTimestamp, 10);

        byte[] byteArraySensorData = new byte[sensorDataLength];
        System.arraycopy(responseContent, deviceIDLength + timestampLength + 3 , byteArraySensorData, 0, byteArraySensorData.length);

        //
        String sensortype = deviceID.substring(5,7);

        if (sensortype == "01") {
            processDataType1(byteArraySensorData);
        }
        if (sensortype == "02") {
            processDataType2(byteArraySensorData);
        }
        if (sensortype == "03") {
            processDataType3(byteArraySensorData);
        }
        if (sensortype == "04") {
            processDataType4(byteArraySensorData);
        }

    }
    public void handleOrder4Response(byte[] responseContent) {
        int deviceIDLength = byteToUnsignedValue(responseContent[0]);
        int timestampLength = 4;
        int sensorDataLength = byteToUnsignedValue(responseContent[1 + deviceIDLength + 1 + timestampLength + 1 - 1]);

        byte[] byteArrayDeviceID = new byte[deviceIDLength];
        System.arraycopy(responseContent, 1, byteArrayDeviceID, 0, byteArrayDeviceID.length);
        String deviceID = byteArrayToString(byteArrayDeviceID, 16);

        byte[] byteArrayTimestamp = new byte[timestampLength];
        System.arraycopy(responseContent, deviceIDLength + 2, byteArrayTimestamp, 0, byteArrayTimestamp.length);
        String timestamp = byteArrayToString(byteArrayTimestamp, 10);

        byte[] byteArraySensorData = new byte[sensorDataLength];
        System.arraycopy(responseContent, deviceIDLength + timestampLength + 3 , byteArraySensorData, 0, byteArraySensorData.length);

        //
        String sensortype = deviceID.substring(5,7);

        // 由对应的方法处理数据
        if (sensortype == "01") {
            processDataType1(byteArraySensorData);
        }
        if (sensortype == "02") {
            processDataType2(byteArraySensorData);
        }
        if (sensortype == "03") {
            processDataType3(byteArraySensorData);
        }
        if (sensortype == "04") {
            processDataType4(byteArraySensorData);
        }

    }

    public void handleOrder5Response(byte[] responseContent) {
        int flag = byteToUnsignedValue(responseContent[0]);

    }

    public void handleOrder6Response(byte[] responseContent) {
        int netMaskIDLength = byteToUnsignedValue(responseContent[0]);
        int deviceIDLength = byteToUnsignedValue(responseContent[1 + netMaskIDLength + 1 - 1]);

        byte[] byteArrayNetMaskID = new byte[netMaskIDLength];
        System.arraycopy(responseContent, 1, byteArrayNetMaskID, 0, byteArrayNetMaskID.length);
        String netmaskID = byteArrayToString(byteArrayNetMaskID, 10);
        byte[] byteArrayDeviceID = new byte[deviceIDLength];
        System.arraycopy(responseContent, netMaskIDLength + 2, byteArrayDeviceID, 0, byteArrayDeviceID.length);
        String deviceID = byteArrayToString(byteArrayDeviceID, 16);
        byte[] byteArrayTimestamp = new byte[4];
        System.arraycopy(responseContent, netMaskIDLength + deviceIDLength + 2, byteArrayNetMaskID, 0, 4);
        int timestamp = byteArrayToInt(byteArrayTimestamp, 0, byteArrayTimestamp.length - 1);

    }

    public void handleOrder7Response(byte[] responseContent) {
        int flag = byteToUnsignedValue(responseContent[responseContent.length - 1]);
        byte[] byteArrayDeviceID = new byte[responseContent.length - 1];
        System.arraycopy(responseContent, 0, byteArrayDeviceID, 0, byteArrayDeviceID.length);
        String deviceID = byteArrayToString(byteArrayDeviceID, 16);

    }

    //处理Socket收到的信息
    public void socketInfoProcess(List<Byte> byteArrayList) {
        String date = dateformat.format(System.currentTimeMillis());
        String yearMonth = date.substring(0,7);//如2019-03

        Equipment equipmentData;//added0521
        // 从网关发来的只带一个字节有效数据的帧长度就是8，比这个小的就是坏掉的或无关的
        while (byteArrayList.size() >= 8) {
            int orderType = byteToUnsignedValue(byteArrayList.get(2)); // 指令码
            int responseLength = byteToUnsignedValue(byteArrayList.get(3)); // 回复内容长度
            byte[] responseContent = new byte[responseLength - 1];  // 不包括校验和(扣掉1位校验和)
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


            // 检查校验和
            int check = 0;
            for (byte b : responseContent) {
                check = check + b;
                if(check > 256) {
                    check = check % 256;
                }
            }
            if (check != checkSum) {
                break;
            }


            // 将回复信息放到responseContent
            for (int i = 0; i < responseLength - 1;++i) {
                responseContent[i] = byteArrayList.get(i + 3);
            }

            if (orderType == 1) {
                handleOrder1Response(responseContent);  // responseContent包括1位通信类型和n位网关号
            }
            else if (orderType == 2) {
                handleOrder2Response(responseContent);  // responseContent包括5位设备ID和1位成功失败标识
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

        }
    }


}
