package com.duing.netty.heartbeat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Random;

public class HeartBeatClient {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new StringEncoder());

                        ch.pipeline().addLast(new HeartBeatClientHandler());
                    }
                });

        System.out.println("客户端初始化完成");
        try {
            ChannelFuture future = bootstrap.connect("127.0.0.1", 2020).sync();

            String data = "I am alive";
            while (future.channel().isActive()) {
                // 模拟空闲的状态  随机等待时间
                int num = new Random().nextInt(10);
                Thread.sleep(num * 1000);
                System.out.println("等待" + num + "秒后进行下次发送");
                future.channel().writeAndFlush(data);
            }


            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }


    static class HeartBeatClientHandler extends SimpleChannelInboundHandler<String> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            System.out.println("server data: " + msg);

            if ("you are out".equals(msg)) {
                ctx.channel().close();
            }

        }
    }
}
