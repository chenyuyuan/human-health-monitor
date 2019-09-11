package com.test.socketexample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket ss = null;
    Socket socket;
    InputStream in;
    OutputStream out;
    DataInputStream din;
    DataOutputStream dout;
    public Server() {
        try {
            ss = new ServerSocket(10000);
            System.out.println("Waiting for connecting......");
            socket = ss.accept();
            System.out.println("Connected!");
            in = socket.getInputStream();
            out = socket.getOutputStream();
            din = new DataInputStream(in);
            dout = new DataOutputStream(out);
            dout.writeUTF("Hello!");
            String name = din.readUTF();
            String s = din.readUTF();
            System.out.println(name + s);
            in.close();;
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
    }
}