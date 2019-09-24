package com.humanhealthmonitor.rabbitmq;

import java.util.concurrent.ConcurrentLinkedQueue;

public class NetMaskReceiveQueue {

    public static ConcurrentLinkedQueue<String> receiveQueue = new ConcurrentLinkedQueue<>();

}
