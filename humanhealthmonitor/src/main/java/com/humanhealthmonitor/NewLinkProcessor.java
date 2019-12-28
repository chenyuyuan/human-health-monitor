package com.humanhealthmonitor;

import com.humanhealthmonitor.service.UserNetmaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.humanhealthmonitor.MsgQueue.ipNetmaskIDTable;

import static com.humanhealthmonitor.MsgQueue.sendMsgQueue;
import static com.humanhealthmonitor.util.ByteUtils.*;

@Component
public class NewLinkProcessor implements Runnable{

    @Autowired
    private UserNetmaskService userNetmaskService;

    public static NewLinkProcessor newLinkProcessor;
    public NewLinkProcessor(){

    }

    @PostConstruct
    public void init() {
        newLinkProcessor = this;
        newLinkProcessor.userNetmaskService = this.userNetmaskService;
    }


    private Socket socket;
    public void setSocket(Socket socket){ //added0521
        this.socket = socket;
    }
    public void run() {
        try {
            handleSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 与客户端Socket进行通信
    private void handleSocket() throws Exception {
        // HealthDataProcessor healthDataProcessor = new HealthDataProcessor();//实例化信息处理类
        // 如果收到了信息就把信息打印出来
        PrintWriter pw = null;
        System.out.println("NewLinkProcessor: "+socket.getInetAddress() + " has already linked...");
        pw = new PrintWriter(socket.getOutputStream());

        String orderString = "FEFE020101AABB";//询问网关号，固定命令
        byte[] orderByte = toByteArray(orderString);

        //byte[] orderByte = orderString.getBytes();
        OutputStream os = socket.getOutputStream();
        os.write(orderByte);
        os.flush();
        System.out.println("NewLinkProcessor: Send: " + bytesToHexString(orderByte));
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
                System.out.println("NewLinkProcessor: received: " + info);
                System.out.println("NewLinkProcessor: received byteArrayList: " + byteArrayList);
                //socket.shutdownInput();//added 2019/04/08关闭输入流//comment0524
                //socket.shutdownOutput();//added 2019/04/10关闭输出流//comment0524
                socketInfoProcess(byteArrayList);//NullPointException line158,74,28
                break;//added0526
            }
        }
        //关闭相对应的资源
        //pw.close();//comment0524
        //bis.close();//comment0524
        //dis.close();//comment0524
        //socket.close();
        System.out.println("NewLinkProcessor: one newLinkProcessor close safely...");
    }


    //处理Socket收到的信息
    public void socketInfoProcess(List<Byte> byteArrayList) {
        System.out.println("byteArrayList.length:" + byteArrayList.size());
        while (byteArrayList.size() >= 8) {
            int orderType = byteToUnsignedValue(byteArrayList.get(2)); // 指令码
            int responseLength = byteToUnsignedValue(byteArrayList.get(3)); // 回复内容长度
            byte[] responseContent = new byte[responseLength];  // 不包括校验和(扣掉1位校验和)
            int checkSum = byteToUnsignedValue(byteArrayList.get(byteArrayList.size()-3)); // 校验和



            if (byteArrayList.get(0) != (byte) 0xFE || byteArrayList.get(1) != (byte) 0xFE) {
                System.out.println("NewLinkProcessor(socketInfoProcess): The byte head is not FEFE");
                byteArrayList.remove(0);
                break;
            }
            if (byteArrayList.size() != responseLength + 7) {
                System.out.println("NewLinkProcessor(socketInfoProcess): The byte length is wrong");
                byteArrayList.remove(0);
                break;
            }
            if (byteArrayList.get(byteArrayList.size() - 2) != (byte) 0xAA || byteArrayList.get(byteArrayList.size() - 1) != (byte) 0xBB) {
                System.out.println("NewLinkProcessor(socketInfoProcess): The byte tale is not AABB");
                byteArrayList.remove(0);
                break;
            }

            // 将回复信息放到responseContent
            for (int i = 0; i < responseLength; ++i) {
                responseContent[i] = byteArrayList.get(i + 4);
            }

            //校验和计算
            int check = 0;
            for (int i = 0; i < responseLength; ++i) {
                check = check + responseContent[i];
                if(check > 256) {
                    check = check % 256;
                }
            }
            if (check != checkSum) {
                System.out.println("NewLinkProcessor(socketInfoProcess): {check/checkSum: "+check+" "+checkSum+"}data check error...");
                byteArrayList.remove(0);
                break;
            }


            System.out.println("NewLinkProcessor(socketInfoProcess): orderType: " + orderType);
            List<Byte> dataList = byteArrayList.subList(6, 6 + responseLength);//取出校验成功的数据区数据，放到dataList中

            System.out.println("NewLinkProcessor(socketInfoProcess): dataList of order 01:"+dataList);

            int byteArrayNetMaskIdLength = responseContent.length - 1;
            int RadixNetMaskId = 10;
            byte[] byteArrayNetMaskId = new byte[byteArrayNetMaskIdLength];
            System.arraycopy(responseContent, 1, byteArrayNetMaskId, 0, byteArrayNetMaskIdLength);
            // reverse ?
            String StringNetMaskId = byteArrayToString(byteArrayNetMaskId, RadixNetMaskId);

            int netMaskId = Integer.parseInt(StringNetMaskId, RadixNetMaskId);


            System.out.println("NewLinkProcessor(socketInfoProcess): netMaskId: "+netMaskId);
            //检查如果socketTasks数组中对应于netmask编号的这是否正在运行，如果正在运行则本线程结束，提示已经有网关号为netmask的，冲突。。
            MsgQueue.socketTasks[netMaskId-1] = new SocketTask();//这里新建的一定是走else路线////////还需想想如果冲突的来了怎么办，如何不予处理
            if(MsgQueue.socketTasks[netMaskId-1].getTaskNum() == netMaskId) { //说明已经在运行
                System.out.println("NewLinkProcessor(socketInfoProcess): conflict, netmask"+netMaskId+" already exist,please check your setting...");
            }
            else {
                System.out.println("NewLinkProcessor(socketInfoProcess): socketTasks"+netMaskId+" ready to start...");
                //socketTasks[netMaskId-1] = new SocketTask();

                String socketIp = socket.getInetAddress().getHostAddress();
                int socketPort = socket.getPort();
                String socketAddress = socketIp + ":" + socketPort;
                System.out.println("NewLinkProcessor(socketInfoProcess): ip address is " + socketAddress);
                ipNetmaskIDTable.put(socketAddress, netMaskId);

                //System.out.println("<getSocket():>1" + (MsgQueue.socketTasks[netMaskId - 1].getSocket() == null));

                MsgQueue.socketTasks[netMaskId-1].setSocket(socket);
                MsgQueue.socketTasks[netMaskId-1].setTaskNum(netMaskId);

                //System.out.println("<getSocket():>2" + (MsgQueue.socketTasks[netMaskId - 1].getSocket()==null));

                new Thread(MsgQueue.socketTasks[netMaskId-1]).start();
                System.out.println(socketIp+socketPort+netMaskId);
                newLinkProcessor.userNetmaskService.updateNetmaskIpPort(socketIp,socketPort,netMaskId);

            }

            //内圈while处理完之后
            System.out.println("NewLinkProcessor(socketInfoProcess): Info: data section analyze completed...");
            //可能存在dataLength写的值大于实际值，出现dataLength + 9 < byteArrayList.size()的情况，于是加了一个判断来避免异常
            if(responseLength + 9 < byteArrayList.size()) {//如果小于，则截取然后继续，针对的是一个数据包内多个回复帧的情况
                byteArrayList = byteArrayList.subList(responseLength + 9, byteArrayList.size());
            }
            else {//如果数据分析完毕，直接跳出外圈while循环
                System.out.println("NewLinkProcessor(socketInfoProcess): Info: package analyze completed....");
                break;
            }
            //校验和验证，验证成功继续，然后是判断设备类型，这里先简易地写死，00是血压，01是温度。02是血氧，
            //然后把数据库里面的设备号改成网关一样的，或者设备上加字段，设备号之外再加网关和网关内设备序号
            //取出数据后存入influxdb
        }
    }

}
