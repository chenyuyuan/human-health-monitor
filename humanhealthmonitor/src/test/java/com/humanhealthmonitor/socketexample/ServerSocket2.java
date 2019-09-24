package com.humanhealthmonitor.socketexample;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.humanhealthmonitor.util.ByteUtils.*;

public class ServerSocket2 {
    public ServerSocket2() {}

    ServerSocket ss;
    Socket socket;
    InputStream din;
    OutputStream dout;

    public void run() throws IOException {
        while (true) {
            try {
                ServerSocket ss = new ServerSocket(8081);
                System.out.println("Waiting for message from cloud......");
                Socket socket = ss.accept();
                System.out.println("Connected!");

                InputStream din = socket.getInputStream();
                OutputStream dout = socket.getOutputStream();

                byte[] bytes = new byte[1];
                String info = "";
                List<Byte> byteArrayList = new ArrayList<>();

                String sendMessage = "FEFE03010501AABB";
                byte[] sendMessageByteArray = toByteArray(sendMessage);
                System.out.println("send: " + byteArrayToString(sendMessageByteArray, 16));
                dout.write(sendMessageByteArray);
                dout.flush();

                while (din.read(bytes) != -1) {
                    info += bytesToHexString(bytes);
                    //System.out.println(info);
                    byteArrayList.add(bytes[0]);
                    byte[] receiveByteArray = new byte[byteArrayList.size()];
                    for (int i = 0; i < receiveByteArray.length - 1;++i) {
                        receiveByteArray[i] = byteArrayList.get(i);
                    }
                    System.out.println("rec: " + byteArrayToString(receiveByteArray, 16));
                    if (din.available() == 0) {
                        byteArrayList.clear();
                        dout.flush();
                    }
                }

                //byte[] receivedMessage = din.readAllBytes();
                //System.out.println("received message: " + byteArrayToString(receivedMessage, 16));
                //byte[] responseMessage = toByteArray(getResponse(byteArrayToString(receivedMessage, 16)));
                //dout.write(responseMessage);

                din.close();
                dout.close();
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {

            }
        }

    }
    public static void main(String[] args) throws IOException {
        ServerSocket2 serverSocket2 = new ServerSocket2();
        serverSocket2.run();
    }

}