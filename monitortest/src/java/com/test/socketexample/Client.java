package com.test.socketexample;

import java.io.*;
import java.net.Socket;

public class Client {
    Socket socket;
    InputStream in;
    OutputStream out;
    DataInputStream din;
    DataOutputStream dout;
    public Client() {
        try {
            socket = new Socket("127.0.0.1", 10000);
            in = socket.getInputStream();
            out = socket.getOutputStream();
            din = new DataInputStream(in);
            dout = new DataOutputStream(out);
            din.readUTF();
            dout.writeUTF("cyuyuan");
            dout.writeUTF("hello, world!");
            in.close();;
            out.close();
            din.close();
            dout.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Client client = new Client();
    }
}