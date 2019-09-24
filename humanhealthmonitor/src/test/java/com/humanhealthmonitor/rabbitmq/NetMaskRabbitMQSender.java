package com.humanhealthmonitor.rabbitmq;

import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;


public class NetMaskRabbitMQSender implements Runnable {
    private final static String EXCHANGE_NAME = "amq.direct";
    private final static String QUEUE_NAME = "health_queue";


    public void run() {
        while(true) {
            try {
                rabbitSender();
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    public void rabbitSender() throws IOException, TimeoutException {
        String QUEUE_NAME = "health_queue"; // 设置网关号作为队列名
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("140.143.232.52");
        factory.setUsername("Andy");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "FEFE0401040005AABB";
        while(!NetMaskReceiveQueue.receiveQueue.isEmpty()) {
            message = NetMaskReceiveQueue.receiveQueue.poll();
        }
        byte[] orderByte = toByteArray(message);
        channel.basicPublish("", QUEUE_NAME, null, orderByte);
    }

    private byte[] toByteArray(String hexString) {
        if (hexString.equals("")) {
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
}
