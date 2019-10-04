package com.example.humanhealthmonitor;

import com.example.humanhealthmonitor.pojo.Equipment;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.humanhealthmonitor.MsgQueue.sendMsgQueue;
import static com.example.humanhealthmonitor.MsgQueue.socketTasks;

public class NewLinkProcessor implements Runnable{

    private Socket socket;

    public void setSocket(Socket socket){//added0521
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
//        HealthDataProcessor healthDataProcessor = new HealthDataProcessor();//实例化信息处理类
        //如果收到了信息就把信息打印出来
        PrintWriter pw = null;
        System.out.println("NewLinkProcessor: "+socket.getInetAddress() + " has already linked...");
        pw = new PrintWriter(socket.getOutputStream());
//        pw.println("FEFE0401040005AABB");
//        pw.flush();
//        String orderString = "FEFE0401040005AABB";
//        while (sendMsgQueue.isEmpty())//为空则线程休眠
//        {
//            Thread.sleep(1000);//1秒
//        }
        String orderString = "FEFE0400010001AABB";//询问网关号，固定命令
        byte[] orderByte = toByteArray(orderString);

//        byte[] orderByte = orderString.getBytes();
        OutputStream os = socket.getOutputStream();
        os.write(orderByte);
        os.flush();
        System.out.println("NewLinkProcessor: Send: " + bytesToHexString(orderByte));
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
                System.out.println("NewLinkProcessor: received: " + info);
                System.out.println("NewLinkProcessor: received byteArrayList: " + byteArrayList);

//                socket.shutdownInput();//added 2019/04/08关闭输入流//comment0524
//                socket.shutdownOutput();//added 2019/04/10关闭输出流//comment0524

                socketInfoProcess(byteArrayList);/////NullPointException line158,74,28
                break;//added0526
            }
        }


        //关闭相对应的资源
//        pw.close();//comment0524
//        bis.close();//comment0524
//        dis.close();//comment0524

//        socket.close();
        System.out.println("NewLinkProcessor: one newLinkProcessor close safely...");
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
            System.out.println("NewLinkProcessor: toByteArray(): this hexString is empty");
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
    public int byteToUsignedValue(Byte b) {
        int bInt = (int) b;
        if (bInt >= 0) {
            return bInt;
        } else {
            return (bInt + 256);
        }
    }

    //处理Socket收到的信息
    public void socketInfoProcess(List<Byte> byteArrayList) {
        while (byteArrayList.size() >= 10) {
            if (byteArrayList.get(0) == (byte) 0xFE && byteArrayList.get(1) == (byte) 0xFE) {
                int dataLength = byteToUsignedValue(byteArrayList.get(4)) * 256 + byteToUsignedValue(byteArrayList.get(5));//获取传感器数据长度
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
                            System.out.println("NewLinkProcessor: check pass...");
//                            int netMaskId = byteToUsignedValue(byteArrayList.get(2));//伪网关id，00
                            int orderType = byteToUsignedValue(byteArrayList.get(3));//04是全部设备信息，03是指定设备信息
                            System.out.println("NewLinkProcessor: orderType: " + orderType);
                            List<Byte> dataList = byteArrayList.subList(6, 6 + dataLength);//取出校验成功的数据区数据，放到dataList中

                            System.out.println("NewLinkProcessor: dataList of order 01:"+dataList);

                            int netMaskId = byteToUsignedValue(byteArrayList.get(6));
                            System.out.println("NewLinkProcessor: netMaskId: "+netMaskId);
                            //检查如果socketTasks数组中对应于netmask编号的这是否正在运行，如果正在运行则本线程结束，提示已经有网关号为netmask的，冲突。。
                            socketTasks[netMaskId-1] = new SocketTask();//这里新建的一定是走else路线////////还需想想如果冲突的来了怎么办，如何不予处理
                            if(socketTasks[netMaskId-1].getTaskNum() == netMaskId)//说明已经在运行
                            {
                                System.out.println("NewLinkProcessor: conflict, netmask"+netMaskId+" already exist,please check your setting...");
                            }
                            else
                            {
                                System.out.println("NewLinkProcessor: socketTasks"+netMaskId+" ready to start...");
//                                socketTasks[netMaskId-1] = new SocketTask();
                                socketTasks[netMaskId-1].setSocket(socket);
                                socketTasks[netMaskId-1].setTaskNum(netMaskId);
                                new Thread(socketTasks[netMaskId-1]).start();
                            }

                            //内圈while处理完之后
                            System.out.println("NewLinkProcessor: Info: data section analyze completed...");
                            //可能存在dataLength写的值大于实际值，出现dataLength + 9 < byteArrayList.size()的情况，于是加了一个判断来避免异常
                            if(dataLength + 9 < byteArrayList.size())//如果小于，则截取然后继续，针对的是一个数据包内多个回复帧的情况
                            {
                                byteArrayList = byteArrayList.subList(dataLength + 9, byteArrayList.size());
                            }
                            else//如果数据分析完毕，直接跳出外圈while循环
                            {
                                System.out.println("NewLinkProcessor: Info: package analyze completed....");
                                break;
                            }
                            //校验和验证，验证成功继续，然后是判断设备类型，这里先简易地写死，00是血压，01是温度。02是血氧，
                            //然后把数据库里面的设备号改成网关一样的，或者设备上加字段，设备号之外再加网关和网关内设备序号
                            //取出数据后存入influxdb

                        } else {//校验和错误，去掉最前面的一个字节，进入下一个循环
                            System.out.println("NewLinkProcessor: data check error...");
                            byteArrayList.remove(0);
                        }
                    } else {//结尾格式不是AABB，证明数据损坏，去掉最前面的一个字节，进入下一个循环
                        System.out.println("NewLinkProcessor: package tail is broken...");
                        byteArrayList.remove(0);
                    }

                } else {//字节总长度达不到，证明数据可能损坏，去掉最前面的一个字节，进入下一个循环
                    System.out.println("NewLinkProcessor: package length error compared to dataLength, maybe package is broken...");
                    byteArrayList.remove(0);
//                    break;
                }
//                byteArrayList = byteArrayList.subList(dataLength + 9, byteArrayList.size());
                //可能存在dataLength写的值大于实际值，出现dataLength + 9 < byteArrayList.size()的情况，于是加了一个判断来避免异常
//                if(dataLength + 9 < byteArrayList.size())//如果小于，则截取然后继续
//                {
//                    byteArrayList = byteArrayList.subList(dataLength + 9, byteArrayList.size());
//                }
//                else//否则直接跳出while循环
//                {
//                    System.out.println("Info: package is deserted...no valuable data...");
//                    break;
//                }
            } else {//如果数据头不是FEFE，去掉前面的一个字节，进入下一个循环
                byteArrayList.remove(0);
            }
            //外圈while循环每圈一定会执行的位置
        }
    }

}
