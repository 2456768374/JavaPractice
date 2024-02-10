package Netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Auther: Kritu
 * @Date: 2024/2/7 10:39
 * @Description: 基于Netty的简单TCP服务端
 */

/*
* EventLoop：处理I/O操作的核心部分，负责监听网络事件、执行任务和处理I/O操作等。
* 每个EventLoop都负责处理 **一组通道** 的所有I/O事件。
* 每个EventLoop都运行在一个单独的线程中，用于异步地处理I/O操作。
*
* EventLoopGroup：用于管理事件循环（EventLoop）的组件。EventLoopGroup通常分为两种类型：
* Boss EventLoopGroup（老板事件循环组）：
* 负责接受传入的连接，并将接受的连接注册到工作线程的EventLoop上。
* 通常用于处理服务端的启动过程和连接的接受过程。
* Worker EventLoopGroup（工作事件循环组）：
* 负责处理已经被接受的连接的I/O事件。通常用于处理已经建立的连接的读写等操作。
* */
public class HelloServer {
    public static void main(String[] args) {
        // 创建一个新的ServerBootstrap实例，用于启动和配置服务器。
        new ServerBootstrap()
                // 设置用于处理I/O操作的EventLoopGroup。
                .group(new NioEventLoopGroup())
                // 指定服务器的ServerSocketChannel实现。
                .channel(NioServerSocketChannel.class)
                // 决定worker能干哪些操作。
                .childHandler(

                        new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // 向Channel的pipeline中添加一个StringDecoder处理器
                        ch.pipeline().addLast(new StringDecoder());
                        // 用于处理Channel的入站事件。
                        // 重写了channelRead方法，用于处理接收到的消息。
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
                                System.out.println(msg);
                            }
                        });
                    }
                }).bind(8080);
    }
}
