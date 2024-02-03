package TCPExample.upload;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Auther: Kritu
 * @Date: 2024/2/2 21:33
 * @Description: 文件上传-客户端
 */
public class Client {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(88);
        Socket clientSocket = serverSocket.accept();

    }
}
