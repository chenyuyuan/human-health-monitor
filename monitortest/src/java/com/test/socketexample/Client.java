package com.test.socketexample;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {
    Socket socket;
    InputStream in;
    OutputStream out;
    DataInputStream din;
    DataOutputStream dout;
    public Client() {}

    public void run() {
        try {
            // socket = new Socket("140.143.232.52", 8081);
            socket = new Socket("127.0.0.1", 8081);
            in = socket.getInputStream();
            out = socket.getOutputStream();
            din = new DataInputStream(in);
            dout = new DataOutputStream(out);
            din.readUTF();
            dout.writeUTF("cyuyuan");
            dout.writeUTF("hello, world!");
            in.close();
            out.close();
            din.close();
            dout.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
}