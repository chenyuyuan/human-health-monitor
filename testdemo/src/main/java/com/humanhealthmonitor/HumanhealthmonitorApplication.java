package com.humanhealthmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

@SpringBootApplication
@MapperScan("com.example.humanhealthmonitor.mapper")
//@ComponentScan(basePackages = "com.example.humanhealthmonitor")
public class HumanhealthmonitorApplication {
    // public static ServerSocket serverSocket;//

    public static void main(String[] args) {
        SpringApplication.run(HumanhealthmonitorApplication.class, args);
        // 测试于2019.04.23
        // sendMsgQueue.offer("element1");//元素插入队尾
        // sendMsgQueue.offer("element2");
        // String str = sendMsgQueue.poll();//获取并移除此队列的头，如果此队列为空，则返回 null
        // String str = sendMsgQueue.peek();//获取但不移除队列头
        // System.out.println(str);
        // String str2 = sendMsgQueue.poll();
        // System.out.println(str2);
        // String str3 = sendMsgQueue.poll();
        // if (str3 == null){
        //     System.out.println("str3null");
        // }else{
        //     System.out.println(str3);
        // 添加于2019.04.03
        // serverSocketTest();
        // try{
        //     serverSocket = new ServerSocket(14900);
        // }catch (IOException e) {
        //     e.printStackTrace();
        // }

        //added0525
        MsgQueue.inetAddressArray = new ArrayList<>();
        MsgQueue.inetAddressArray.clear();

        //初始化protocolType为0即未工作
        for(int i = 0;i <32;i++)
        {
            MsgQueue.protocolState[i] = 0;
        }

        //初始化sendMsgQueue
        for(int i = 0;i <32;i++)
        {
            ConcurrentLinkedQueue<String> conQueue = new ConcurrentLinkedQueue<>();
            MsgQueue.sendMsgQueue.add(conQueue);
        }
        // keepRabbitListening();
        // 添加于2019.04.04
        keepSocketListening();

    }

    //方法添加于2019.04.04
    public static void keepSocketListening() {
        try {
            new Thread(new RabbitReceiver()).start();
            new Thread(new RabbitSender()).start();
            // System.out.println("keepRabbitListening...");
            // 给rabbitsend开一个线程，创建一个全局队列，向里面发命令，收到命令后自动发送给网关

            ServerSocket serverSocket = new ServerSocket(14900);
            System.out.println("Application: Server Listening...");

            while (true) {//循环监听
                System.out.println("Application: Socket ready to accept...");
                Socket socket = serverSocket.accept();//侦听并接受到此套接字的连接,返回一个Socket对象
                // socket.close();//////////////////////////
                // SocketTask socketTask = new SocketTask();//////////////////////
                // new Thread(new SocketTask()).start();/////////////////
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
                    // 记录下来，如果与已经有的相同，则不继续，同时，在每个socketttask快结束了时，把其对应的inet置为null
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

    //    //方法添加于20190519
    //    public static void keepRabbitListening() {
    //        try {
    //            new Thread(new RabbitReceiver()).start();
    //            System.out.println("keepRabbitListening...");
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //    }

    //    //方法添加于2019.04.03
    //    public static void serverSocketTest()
    //    {
    //        try {
    //            //获取本机ip和名称
    //            InetAddress addr = InetAddress.getLocalHost();
    //            String ip = addr.getHostAddress();
    //            String hostName = addr.getHostName();
    //            System.out.println(ip);
    //            System.out.println(hostName);
    //
    //            ServerSocket serverSocket = new ServerSocket(14900);
    //            System.out.println("server has started..waiting for connection..");
    //            Socket socket = serverSocket.accept();//侦听并接受到此套接字的连接,返回一个Socket对象
    //
    //            //根据输入输出流和客户端连接
    //            InputStream inputStream=socket.getInputStream();//得到一个输入流，接收客户端传递的信息
    //            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);//提高效率，将自己字节流转为字符流
    //            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//加入缓冲区
    //            String temp = null;
    //            String info = "";
    //            System.out.println("connection has been established..");
    //            //如果收到了信息就把信息打印出来
    //            while((temp = bufferedReader.readLine()) != null){
    //                info += temp;
    //                System.out.println("information from client is:"+info+",current client ip = "+socket.getInetAddress().getHostAddress());
    //            }
    //            if (info.equals(""))//如果没收到信息，就说没收到
    //            {
    //                System.out.println("no information from client,current client ip = "+socket.getInetAddress().getHostAddress());
    //            }
    //
    //            OutputStream outputStream=socket.getOutputStream();//获取一个输出流，向服务端发送信息
    //            PrintWriter printWriter=new PrintWriter(outputStream);//将输出流包装成打印流
    //            printWriter.print("hello,your information has been received by server..");
    //            printWriter.flush();
    //            socket.shutdownOutput();//关闭输出流
    //
    //            //关闭相对应的资源
    //            printWriter.close();
    //            outputStream.close();
    //            bufferedReader.close();
    //            inputStream.close();
    //            socket.close();
    //
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        }
    //    }

    //    //方法添加于2019.03.11
    //    @Bean(name = "multipartResolver")
    //    public MultipartResolver multipartResolver()
    //    {
    //        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
    //        resolver.setDefaultEncoding("UTF-8");
    //        resolver.setResolveLazily(true);//resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常  
    //        resolver.setMaxInMemorySize(40960);
    //        resolver.setMaxUploadSize(5 * 1024 * 1024);//上传文件大小 5M 5*1024*1024  
    //        return resolver;
    //    }

}

