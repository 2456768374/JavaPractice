package TCPExample.upload;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @Auther: Kritu
 * @Date: 2024/2/2 21:33
 * @Description: 文件上传-服务端
 */
public class Server {
    public static void main(String[] args) throws IOException {
        Socket serverClient = new Socket(InetAddress.getLocalHost(), 88);
        // 创建输入流读取磁盘文件

    }
}
