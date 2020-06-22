package com.duing.netty.helloworld;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * EventLoopGroup 是对应Reactor的时间循环组
 * ServerBootstrap 是配置参数的启动对象
 *   客户端通道  需要使用childHandler设置
 *       设置时  需要创建ChannelInitializer  并且声明其泛型
 *       实现初始化方法时   拿到管道  增加自定义的处理器
 *
 *   异步启动  并且  关闭方式也设置为异步
 */
public class NettyServer {

    public static void main(String[] args) {

        // 创建两个Reactor  构建主从Reactor模型
        //   用来管理channel  监听事件  是无限循环的事件组(线程池)
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();


        // 服务端的引导程序/启动对象
        ServerBootstrap  serverBootstrap = new ServerBootstrap();

        // 设置相关的参数
        // 方法返回类型为  对象自身  这提供了链式编程的使用方法
        serverBootstrap.group(bossGroup,workerGroup)
                       // 声明当前使用的通道类型
                       //     netty                        nio                    bio
                       //  NioServerSocketChannel  <-  ServerSocketChannel  <- ServerSocket
                       // 底层是通过反射进行调用的
                       .channel(NioServerSocketChannel.class)
                       //  设置前面通道的处理器  使用netty提供的日志打印处理器
                       .handler(new LoggingHandler(LogLevel.INFO))
                       //  定义客户端连接处理器的使用
                       //    此方法需要参数  ChannelInitializer  通道初始化器
                       //        初始化器  要处理的是客户端通道  所以泛型设置为SocketChannel
                       //        此类为抽象类  需要实现其抽象方法initChannel  (Alt+ Enter快捷键)
                       .childHandler(new ChannelInitializer<SocketChannel>() {
                           @Override
                           protected void initChannel(SocketChannel ch) throws Exception {
                                // 通过channel 获取管道  pipeline
                                //   通道代表的是  连接的角色   管道代表的是  处理业务的逻辑管理
                                //      管道相当于链表 将不同的处理器连接起来  管理的是处理器的顺序
                                //      使用时常常使用尾插法   addLast 将处理器增加至尾部
                                ch.pipeline().addLast(new NettyServerHandler());
                           }
                       });

        System.out.println("服务端初始化完成");

        // 启动并设置端口号  但需要使用sync异步启动
        try {
            ChannelFuture future = serverBootstrap.bind(8888).sync();
            // 将关闭通道的方式 也设置为异步的
            //    阻塞finally中的代码执行
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 优雅关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
