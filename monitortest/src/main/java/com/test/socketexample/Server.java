package com.test.socketexample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    public Server() {}

    public void run() {
        try {
            ServerSocket ss = new ServerSocket(8081);
            System.out.println("Waiting for connecting......");
            Socket socket = ss.accept();
            System.out.println("Connected!");
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            DataInputStream din = new DataInputStream(in);
            DataOutputStream dout = new DataOutputStream(out);
            dout.writeUTF("Hello!(From server)");
            String name = din.readUTF();
            String s = din.readUTF();
            System.out.println(name + s);
            in.close();
            out.close();
            din.close();
            dout.close();
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}