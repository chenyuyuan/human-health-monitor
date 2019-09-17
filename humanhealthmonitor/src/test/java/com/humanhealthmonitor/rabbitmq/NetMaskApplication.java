package com.humanhealthmonitor.rabbitmq;

public class NetMaskApplication {
    public static void main(String[] args) {
        Runnable runnableNetMaskRabbitMQReceiver = new NetMaskRabbitMQReceiver();
        Thread threadNetMaskRabbitMQReceiver = new Thread(runnableNetMaskRabbitMQReceiver);
        threadNetMaskRabbitMQReceiver.start();
        Runnable runnableCloudRabbitMQReceiver = new CloudRabbitMQReceiver();
        Thread threadCloudRabbitMQReceiver = new Thread(runnableCloudRabbitMQReceiver);
        threadCloudRabbitMQReceiver.start();
    }
}
