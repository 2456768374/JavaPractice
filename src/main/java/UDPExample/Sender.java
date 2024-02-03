package UDPExample;

import java.io.IOException;
import java.net.*;

/**
 * @Auther: Kritu
 * @Date: 2024/2/3 16:47
 * @Description:
 */
public class Sender {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(9998);
        byte[] data = "我是发送端的数据".getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, InetAddress.getByName("192.168.1.8"), 9999);
        datagramSocket.send(datagramPacket);
        datagramSocket.close();

    }
}
