package com.duing.socket.nio;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NioServer {

    public static void main(String[] args) throws Exception {

        // 创建一个服务端的通道  调用open方法获取
        ServerSocketChannel serverChannel = ServerSocketChannel.open();

        // 绑定ip地址和端口号
        SocketAddress address = new InetSocketAddress("127.0.0.1",4321);
        serverChannel.socket().bind(address);

        // 接收客户端的连接
        SocketChannel socketChannel = serverChannel.accept();


        // 数据处理都要通过buffer
        ByteBuffer buffer = ByteBuffer.allocate(128);
        buffer.put("hello client".getBytes());

        buffer.flip();
        socketChannel.write(buffer);


        // 把数据读入到readBuffer中
        ByteBuffer readBuffer = ByteBuffer.allocate(128);
        socketChannel.read(readBuffer);

        readBuffer.flip();
        StringBuffer stringBuffer = new StringBuffer();
        while (readBuffer.hasRemaining()){
            stringBuffer.append((char)readBuffer.get());
        }

        System.out.println("client data:" + stringBuffer.toString());
        socketChannel.close();
        serverChannel.close();

    }
}
