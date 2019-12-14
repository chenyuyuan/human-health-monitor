package com.humanhealthmonitor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MsgQueue {

    // public static ConcurrentLinkedQueue<String> sendMsgQueue = new ConcurrentLinkedQueue<String>();
    // 如果线程很多这个Queue有可能要做成一个数组，元素就是queue
    // public static ConcurrentLinkedQueue<String> sendMsgQueue = new ConcurrentLinkedQueue<>();//comment0524
    // public static ConcurrentLinkedQueue<String>[] sendMsgQueue = new ConcurrentLinkedQueue<String>[32];///无法创建泛型数组，失败的语句
    // public static ArrayList<ArrayList<String>> sendMsgQueue = new ArrayList<ArrayList<String>>();
    public static ArrayList<ConcurrentLinkedQueue<String>> sendMsgQueue = new ArrayList<>();//MODBUS命令发送队列
    public static ConcurrentLinkedQueue<String> sendAMQPQueue = new ConcurrentLinkedQueue<>();//AMQP命令发送队列
    // public static ArrayList<String> addSuccessEqpIdList = new ArrayList<>();// 添加成功的设备号码

    public static ArrayList<String> inetAddressArray = new ArrayList<>(); // socket接收到的连接地址列表

    // 在socketTask里面设置一个变量，默认为“”，一旦获取网管号就改、、、不对，能直接判断socketTasks[i]是否正在运行更好一些
    public static SocketTask[] socketTasks = new SocketTask[32];

    public static int[] protocolState = new int[32]; // 初始化为0即未工作状态，为1代表MODBUS，2代表AMQP

    public static String ipAddress = "39.98.66.7";

    public static Hashtable<String, Integer> ipNetmaskIDTable = new Hashtable<>();

}
