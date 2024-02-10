package Reactor.SingleReactor.client;

/**
 * @Auther: Kritu
 * @Date: 2024/2/10 15:25
 * @Description:
 */
public class Main {
    public static void main(String[] args) {
        new Thread(new NIOClient("127.0.0.1", 2333)).start();
        new Thread(new NIOClient("127.0.0.1", 2333)).start();
    }
}
