package com.humanhealthmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

@RestController
@SpringBootApplication
@MapperScan("com.humanhealthmonitor.mapper")
//@ComponentScan(basePackages = "com.example.humanhealthmonitor")
public class HumanhealthmonitorApplication {
    // public static ServerSocket serverSocket;//

    public static void main(String[] args) {
        SpringApplication.run(HumanhealthmonitorApplication.class, args);

        //added0525
        MsgQueue.inetAddressArray = new ArrayList<>();
        MsgQueue.inetAddressArray.clear();

        //初始化protocolType为0即未工作
        for(int i = 0;i <32;i++) {
            MsgQueue.protocolState[i] = 0;
        }

        //初始化sendMsgQueue
        for(int i = 0;i <32;i++) {
            ConcurrentLinkedQueue<String> conQueue = new ConcurrentLinkedQueue<>();
            MsgQueue.sendMsgQueue.add(conQueue);
        }
        keepSocketListening();

    }

    //方法添加于2019.04.04
    public static void keepSocketListening() {
        try {
            //先不开
            //new Thread(new RabbitReceiver()).start();
            //new Thread(new RabbitSender()).start();
            //先不开
            // System.out.println("keepRabbitListening...");
            // 给rabbitsend开一个线程，创建一个全局队列，向里面发命令，收到命令后自动发送给网关

            ServerSocket serverSocket = new ServerSocket(14900);
            System.out.println("Application: Server Listening...");

            //new Thread(new AutomaticallyFetchDataThread()).start();

            while (true) {//循环监听
                System.out.println("Application: Socket ready to accept...");
                Socket socket = serverSocket.accept();//侦听并接受到此套接字的连接,返回一个Socket对象
                // socket.close();
                // SocketTask socketTask = new SocketTask();
                // new Thread(new SocketTask()).start();
                // 线程号码
                String newInetAddressStr = socket.getInetAddress().toString();
                System.out.println("Application: newInetAddress: "+newInetAddressStr);
                Iterator iterator = MsgQueue.inetAddressArray.iterator();
                int flag = 0;//0说明是新的客户IP，1说明是已有客户IP
                while (iterator.hasNext()) {
                    if (iterator.next().equals(newInetAddressStr)) {
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    // 记录下来，如果与已经有的相同，则不继续，同时，在每个sockettask快结束了时，把其对应的inet置为null
                    System.out.println("Application: Socket accept success,new link...");
                    // new Thread(new SocketTask(socket)).start();//后面可以做成线程池，比如大小为100，接入时候分配一个，并记录下线程编号和网关设备的对应关系
                    // SocketTask socketTask = new SocketTask();////////added0521
                    // socketTask.setSocket(socket);////////added0521
                    // new Thread(socketTask).start();///////////added0521
                    NewLinkProcessor newLinkProcessor = new NewLinkProcessor(); // added0524
                    newLinkProcessor.setSocket(socket); // added0524
                    new Thread(newLinkProcessor).start(); // added0524
                }
                else {
                    System.out.println("Application: Old client ip...");
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

