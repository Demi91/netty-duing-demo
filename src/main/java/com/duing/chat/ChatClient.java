package com.duing.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class ChatClient {

    private SocketChannel channel;

    private Selector selector;

    public ChatClient() {

        try {
            selector = Selector.open();

            SocketAddress address = new InetSocketAddress("127.0.0.1", 6666);
            channel = SocketChannel.open(address);

            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);

            System.out.println("用户" + channel.getLocalAddress() + "上线了");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void sendData(String msg) {

        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        try {
            channel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readData() {
        // 监听到通道有读事件发生

        try {
            int num = selector.select();
            if (num > 0) {

                Set<SelectionKey> set = selector.selectedKeys();
                Iterator<SelectionKey> iterator = set.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();

                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        socketChannel.read(buffer);

                        String msg = new String(buffer.array());
                        System.out.println(msg);

                    }

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        final ChatClient client = new ChatClient();
        new Thread(){
            public void run(){
                while (true){
                    client.readData();

                    try {
                        Thread.currentThread().sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String str = scanner.nextLine();
            client.sendData(str);
        }
    }
}
