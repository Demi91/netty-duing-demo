package com.duing.netty.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MyChatClient {

    public static void main(String[] args) {

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new MyChatClientInitializer());

        try {
            ChannelFuture future = bootstrap.connect("127.0.0.1", 8899).sync();
//            future.channel().closeFuture().sync();

            //通过键盘获取到要发送的数据
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            for (; ; ) {
                String msg = br.readLine();
                future.channel().writeAndFlush(msg + "\r\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
