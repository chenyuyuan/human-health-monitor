package com.humanhealthmonitor.rabbitmq;

import java.util.concurrent.ConcurrentLinkedQueue;

public class SingletonReceiveQueue {
    public static volatile SingletonReceiveQueue singletonReceiveQueue;
    public static ConcurrentLinkedQueue<String> receiveQueue;

    public SingletonReceiveQueue() {
        receiveQueue = new ConcurrentLinkedQueue<>();
    }

    public void getReceiveQueue () {
        if(singletonReceiveQueue == null) {
            synchronized (SingletonReceiveQueue.class) {
                if(singletonReceiveQueue == null) {
                    singletonReceiveQueue = new SingletonReceiveQueue();
                }
            }
        }
    }

}
