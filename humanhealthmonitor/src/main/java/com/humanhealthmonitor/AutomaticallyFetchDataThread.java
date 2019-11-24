package com.humanhealthmonitor;

public class AutomaticallyFetchDataThread implements Runnable {

    @Override
    public void run() {
        try {
            automaticallyFetchData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void automaticallyFetchData() throws InterruptedException {
        //后面改 给每个网关发
        while(true) {
            sendMessage(1, "FEFE020404AABB");
            Thread.sleep(5000);
        }
    }


    //向网关发送获取数据的命令
    public void sendMessage(int netMaskId, String order) {
        if (MsgQueue.protocolState[netMaskId - 1] == 1 ) {
            MsgQueue.sendMsgQueue.get(netMaskId - 1).offer(order);////added0524
        } else if (MsgQueue.protocolState[netMaskId - 1] == 2) {
            MsgQueue.sendAMQPQueue.offer(netMaskId + order);
        } else {
            System.out.println("ObjInfoHallController: Cannot get info, the netMask owing the equipment is offline...");
        }
    }
}
