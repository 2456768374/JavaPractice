package Reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * @Auther: Kritu
 * @Date: 2024/2/7 17:53
 * @Description: 主从多线程Reactor模型
 */


public class MasterSlaveMultiThreadedReactor {
    public static void main(String[] args) throws IOException {
        // 创建主Selector
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8080));
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 创建从Selector和工作线程池
        ExecutorService workerPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ExecutorService bossPool = Executors.newSingleThreadExecutor();
        bossPool.execute(() -> {
            try {
                while (true) {
                    selector.select();
                    Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                    while (keys.hasNext()) {
                        SelectionKey key = keys.next();
                        keys.remove();
                        if (key.isAcceptable()) {
                            ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                            SocketChannel clientChannel = serverChannel.accept();
                            clientChannel.configureBlocking(false);
                            SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);
                            clientKey.attach(new Worker(workerPool, clientKey));
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    static class Worker implements Runnable {
        private final ExecutorService executorService;
        private final SelectionKey key;

        public Worker(ExecutorService executorService, SelectionKey key) {
            this.executorService = executorService;
            this.key = key;
        }

        @Override
        public void run() {
            try {
                SocketChannel clientChannel = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int bytesRead = clientChannel.read(buffer);
                if (bytesRead == -1) {
                    clientChannel.close();
                    key.cancel();
                    return;
                }
                buffer.flip();
                byte[] data = new byte[buffer.remaining()];
                buffer.get(data);
                System.out.println("Received: " + new String(data));
                clientChannel.close();
                key.cancel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

