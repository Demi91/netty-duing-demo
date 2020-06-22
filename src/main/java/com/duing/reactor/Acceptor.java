package com.duing.reactor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Acceptor implements Runnable {

    private ServerSocketChannel serverChannel;

    private Selector selector;

    public Acceptor(ServerSocketChannel serverChannel, Selector selector) {
        this.serverChannel = serverChannel;
        this.selector = selector;
    }

    @Override
    public void run() {

        try {
            SocketChannel socketChannel = serverChannel.accept();

            socketChannel.configureBlocking(false);
            SelectionKey key = socketChannel.register(selector, SelectionKey.OP_READ);
            // 创建handler  用于处理后续的读写事件
            Handler handler = new Handler(key);

//            MultiHandler handler = new MultiHandler(key);
            key.attach(handler);

            // 唤醒阻塞
            selector.wakeup();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
