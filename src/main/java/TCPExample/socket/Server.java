package TCPExample.socket;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Auther: Kritu
 * @Date: 2024/2/2 17:23
 * @Description:
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        Socket clientSocket = serverSocket.accept();
        // 读取内容，读不到则阻塞在这里（即客户端没有发消息，则堵塞在这里）
        InputStream inputStream = clientSocket.getInputStream();
        byte[] buf = new byte[1024];
        int readLen = 0;
        while((readLen = inputStream.read(buf)) != -1){
            System.out.println(new String(buf, 0, readLen));
        }
        // 发内容
        OutputStream outputStream = clientSocket.getOutputStream();
        outputStream.write("我是从Server来的数据".getBytes());
        // 需要设置发送结束标志，否则客户端会卡住
//        clientSocket.shutdownOutput();
        outputStream.close();
        inputStream.close();
        clientSocket.close();
        serverSocket.close();
    }
}
