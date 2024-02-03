package TCPExample.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @Auther: Kritu
 * @Date: 2024/2/2 17:25
 * @Description:
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
        // 发内容
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("hello, server".getBytes());
        // 需要设置发送结束标志，否则服务端会卡住
//        socket.shutdownOutput();
        // 读内容
        InputStream inputStream = socket.getInputStream();
        byte[] buf = new byte[1024];
        int readLen = 0;
        while ((readLen = inputStream.read(buf)) != -1){
            System.out.println(new String(buf, 0, readLen));
        }
        inputStream.close();
        outputStream.close();
        socket.close();
    }
}
