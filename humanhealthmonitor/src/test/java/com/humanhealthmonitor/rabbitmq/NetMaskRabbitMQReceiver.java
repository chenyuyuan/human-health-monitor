package com.humanhealthmonitor.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeoutException;

public class NetMaskRabbitMQReceiver implements Runnable{

    public static volatile ConcurrentLinkedQueue<String> receiveQueue ;
    private final static String EXCHANGE_NAME = "amq.direct";
    private final static String QUEUE_NAME = "health_queue";

    @Override
    public void run() {
        try {
            while(true) {
                rabbitReceiver();
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }


    public void rabbitReceiver() throws IOException, TimeoutException {
        String QUEUE_NAME = "1";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("140.143.232.52");
        factory.setUsername("Andy");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String routingKey = "health";
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, routingKey);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)//body是字节数组类型
                    throws IOException {
//                String message = new String(body, "UTF-8");
//                System.out.println(" [x] Received '" + message + "'");
                ArrayList<Byte> byteArrayList = new ArrayList<>();//字节列表
//                System.out.println("body.length: "+body.length);
                for(int i = 0;i < body.length;i++)
                {
                    byteArrayList.add(body[i]);
                }
                System.out.println("RabbitReceiver: byteArrayList: "+byteArrayList);
                SingletonReceiveQueue.receiveQueue.add(byteArrayToString(body,16));
                //socketInfoProcess(byteArrayList);
            }
        };
    }

    public String byteArrayToString (byte[] byteArray, int radix) {
        return new BigInteger(1, byteArray).toString(radix);
    }

}
