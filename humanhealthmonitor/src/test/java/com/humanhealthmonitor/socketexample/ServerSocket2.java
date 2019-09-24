
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSocket2 {
    public ServerSocket2() {}

    //将1个字节的8个位解析成无符号0-255的值
    public int byteToUnsignedValue(Byte b) {
        int bInt = (int) b;
        if (bInt >= 0) {
            return bInt;
        } else {
            return (bInt + 256);
        }
    }

    //字节转为16进制字符串，如“FE”
    public String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    //int转为两位16进制字符串
    public String byteToHexStringSocketTask(Byte b) {
        int bInt = byteToUnsignedValue(b);
        String str = Integer.toHexString(bInt);
        if(str.length()==1) {
            str = "0" + str;
        }
        return str;
    }

    public byte[] toByteArray(String hexString) {
        if (hexString.equals(""))
            throw new IllegalArgumentException("this hexString must not be empty");
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    public String byteArrayToString (byte[] byteArray, int radix) {
        return new BigInteger(1, byteArray).toString(radix);
    }
    public static String getResponse(String s) {
        if(s.equals("FEFE03010501AABB")) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            return "FEFE0103000202AABB";
        }
        return "00";
    }

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