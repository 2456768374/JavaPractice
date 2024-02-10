package Reactor.SingleReactor.server;

import Reactor.SingleReactor.server.Reactor;

import java.io.IOException;

/**
 * @Auther: Kritu
 * @Date: 2024/2/10 15:23
 * @Description:
 */
public class Main {
    public static void main(String[] args) throws IOException {
        new Thread(new Reactor(2333)).start();
    }
}
