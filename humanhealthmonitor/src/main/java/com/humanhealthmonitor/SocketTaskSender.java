package com.humanhealthmonitor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import static com.humanhealthmonitor.MsgQueue.sendMsgQueue;
import static com.humanhealthmonitor.util.ByteUtils.bytesToHexString;
import static com.humanhealthmonitor.util.ByteUtils.toByteArray;

public class SocketTaskSender implements Runnable {

    private int taskNum = 0;
    private Socket socket;
    public int getTaskNum() {
        return this.taskNum;
    }
    public void setTaskNum(int taskNum) {
        this.taskNum = taskNum;
    }
    public void setSocket(Socket socket){
        this.socket = socket;
    }


    @Override
    public void run() {
        try {
            waitForSending();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void waitForSending() throws InterruptedException, IOException {
        System.out.println("<SocketTaskSernder: waitForSending>");

        OutputStream os = socket.getOutputStream();

        while (true){
            while (sendMsgQueue.get(taskNum-1).isEmpty()) {
                Thread.sleep(1000);
            }
            String orderString = sendMsgQueue.get(taskNum-1).poll();
            byte[] orderByte = toByteArray(orderString);


            os.write(orderByte);

            os.flush();
            System.out.println("SocketTaskSender"+taskNum+": send: " + bytesToHexString(orderByte));
        }
    }
}
