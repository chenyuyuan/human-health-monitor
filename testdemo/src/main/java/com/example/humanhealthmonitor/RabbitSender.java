package com.example.humanhealthmonitor;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import static com.example.humanhealthmonitor.MsgQueue.sendAMQPQueue;

public class RabbitSender implements Runnable{

    public void run() {
        try {
            System.out.println("RabbitSender: RabbitSend start...");
            handleRabbitSend();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleRabbitSend() throws IOException,TimeoutException,Exception {
        String QUEUE_NAME = "";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("140.143.232.52");
        // factory.setPort(5672);
        factory.setUsername("Andy");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 发送1
        // queueDeclare()
        // 第一个参数表示队列名称、第二个参数为是否持久化（true表示是，队列将在服务器重启时生存）、
        // 第三个参数为是否是独占队列（创建者可以使用的私有队列，断开后自动删除）、第四个参数为当所有消费者客户端连接断开时是否自动删除队列、
        // 第五个参数为队列的其他参数
        try{
            while(true)
            {
                while (sendAMQPQueue.isEmpty()) {//为空则线程休眠
                    Thread.sleep(1000);//1秒重新检查队列
                }
                String orderString = sendAMQPQueue.poll();//队列中有消息就取出一条命令
                if (orderString != null && orderString.length() > 8) {
                    int index = orderString.indexOf("FEFE");
                    QUEUE_NAME = orderString.substring(0, index);
                    System.out.println("RabbitSender: QUEUE_NAME: "+QUEUE_NAME);
                }
                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                // String message = "FEFE0401040005AABB";//04报文长度，01网关地址，04指令码查询所有设备，00填补（如果指令码03则这里是设备序号），05校验和
                byte[] orderByte = toByteArray(orderString.substring(2));
                // channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                channel.basicPublish("", QUEUE_NAME, null, orderByte);
                System.out.println("RabbitSender: orderString: "+orderString);
            }
        }catch (Exception e) {
            channel.close();
            connection.close();
            e.printStackTrace();
        }
        // 需要断线重连处理
        // channel.close();
        // connection.close();
        // handleRabbitSend(); END
    }

    private byte[] toByteArray(String hexString) {
        if (hexString.equals("")) {
            System.out.println("RabbitSender: toByteArray(): this hexString is empty");
            throw new IllegalArgumentException("this hexString must not be empty");
        }
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) { //因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }
}
