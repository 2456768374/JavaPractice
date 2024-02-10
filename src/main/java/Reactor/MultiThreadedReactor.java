package Reactor;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: Kritu
 * @Date: 2024/2/7 17:53
 * @Description: 多线程Reactor模型,
 *               主线程负责监听事件（selector.select()），
 *               而读事件的处理被提交到线程池（executor.submit()）中的工作线程中进行。
 */


public class MultiThreadedReactor {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress("localhost", 8080));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);

        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        while (true) {
            // 主线程监听事件的发生
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    // 把客户端socket连接事件注册到主线程selector上
                    // 方便主线程后续监听socket的读取事件
                    client.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    // 将读取操作放入线程池处理
                    executor.submit(() -> {
                        try {
                            SocketChannel client = (SocketChannel) key.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            client.read(buffer);
                            buffer.flip();
                            System.out.println("Received: " + new String(buffer.array()).trim());
                            client.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        }
    }
}

