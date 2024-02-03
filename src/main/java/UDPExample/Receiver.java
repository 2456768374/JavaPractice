package UDPExample;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @Auther: Kritu
 * @Date: 2024/2/3 16:47
 * @Description:
 */
public class Receiver {
    public static void main(String[] args) throws IOException {
        // 1.创建DatagramSocket对象，准备在9999端口接收数据
        DatagramSocket datagramSocket = new DatagramSocket(9999);
        // 2.创建DatagramPacket对象准备接收数据
        // UDP每个数据包最大不超过64KB
        byte[] buf = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
        // 3.调用接收方法，将通过网络传输的对象填充到packet对象中去
        datagramSocket.receive(datagramPacket);

        int length = datagramPacket.getLength();
        byte[] data = datagramPacket.getData();
        String s = new String(data, 0, length);
        System.out.println(s);
    }
}
