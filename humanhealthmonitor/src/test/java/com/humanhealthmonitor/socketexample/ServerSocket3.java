
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocket3 {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket=new ServerSocket(8081);
            System.out.println("服务端已启动，等待客户端连接..");
            Socket socket=serverSocket.accept();
            InputStream inputStream=socket.getInputStream();
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            OutputStream outputStream=socket.getOutputStream();
            DataOutputStream dout = new DataOutputStream(outputStream);

            int count = 100;
            System.out.println("before while");
            while(count-- == 0) {
                Thread.sleep(100000);
                dout.writeUTF("FEFE1122334455667788AABB");
                outputStream.flush();

            }
            System.out.println("after while");

            outputStream.close();
            bufferedReader.close();
            inputStream.close();
            socket.close();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}