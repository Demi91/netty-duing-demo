package com.duing.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;

public class MultiHandler implements Runnable {

    private SelectionKey key;

    private State state;

    private ExecutorService pool;

    public MultiHandler(SelectionKey key) {
        this.key = key;
        this.state = State.READ;
    }

    @Override
    public void run() {

        switch (state) {
            case READ:
                // 将耗时操作放在线程池中执行
                pool.execute(() -> {
                    read();
                });
                break;

            case WRITE:
                write();
                break;
        }
    }

    private void read() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            SocketChannel channel = (SocketChannel) key.channel();
            int num = channel.read(buffer);
            String msg = new String(buffer.array());

            key.interestOps(SelectionKey.OP_WRITE);
            this.state = State.WRITE;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void write() {

        ByteBuffer buffer = ByteBuffer.wrap("hello".getBytes());
        try {
            SocketChannel channel = (SocketChannel) key.channel();
            channel.write(buffer);

            key.interestOps(SelectionKey.OP_READ);
            this.state = State.READ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private enum State {
        READ, WRITE
    }
}
