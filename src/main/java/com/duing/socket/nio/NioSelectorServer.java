package com.duing.socket.nio;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioSelectorServer {

    public static void main(String[] args) throws Exception {
        // 创建一个服务端的通道  调用open方法获取
        ServerSocketChannel serverChannel = ServerSocketChannel.open();

        // 绑定ip地址和端口号
        SocketAddress address = new InetSocketAddress("127.0.0.1", 4321);
        serverChannel.socket().bind(address);

        // 将此channel设置为非阻塞的
        serverChannel.configureBlocking(false);

        // 打开一个选择器
        Selector selector = Selector.open();

        // 将通道注册进选择器中   声明选择器监听的事件
        // 第一个被注册的事件往往是接收连接
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 通过选择器来管理通道
        // 需要感知  被监听的通道是否有操作被触发
        //      当select方法返回值代表要处理的操作个数  >0  开始处理

        while (true) {
            int ready = selector.select();
            if (ready == 0) continue;

            // 进一步获取  要执行的操作集合
            Set<SelectionKey> set = selector.selectedKeys();
            Iterator<SelectionKey> iterator = set.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // 取出操作后  直接移除  避免重复处理
                iterator.remove();

                if (key.isAcceptable()) {

                    // 处理OP_ACCEPT事件
                    // 通过server通道 获取到客户端的对应channel通道并注册  注册写事件
                    SocketChannel socketChannel = serverChannel.accept();
                    socketChannel.configureBlocking(false);

                    socketChannel.register(selector, SelectionKey.OP_WRITE);

                } else if (key.isWritable()) {

                    // 处理OP_WRITE事件
                    // 通过key的channel方法  获取到事件对应的通道
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer writeBuffer = ByteBuffer.allocate(128);
                    writeBuffer.put("hello from 4321".getBytes());
                    writeBuffer.flip();
                    socketChannel.write(writeBuffer);

                    // 再把读事件注册进来
                    key.interestOps(SelectionKey.OP_READ);
                } else if (key.isReadable()) {

                    // 处理OP_READ事件
                    SocketChannel socketChannel = (SocketChannel) key.channel();

                    ByteBuffer readBuffer = ByteBuffer.allocate(128);
                    socketChannel.read(readBuffer);

                    readBuffer.flip();

                    StringBuffer stringBuffer = new StringBuffer();
                    while (readBuffer.hasRemaining()) {
                        stringBuffer.append((char) readBuffer.get());
                    }
                    System.out.println("client data:" + stringBuffer.toString());

                } else if (key.isConnectable()) {
                }

            }
        }

    }
}
