package socket.nio;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SelectorServer {

    public static void main(String[] args) throws Exception {

        // 创建一个服务端管道  open是打开通道的意思
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 绑定ip地址和端口号
        SocketAddress address = new InetSocketAddress("127.0.0.1", 4321);
        serverSocketChannel.socket().bind(address);

        // 把此channel设置为非阻塞的
        serverSocketChannel.configureBlocking(false);

        // 打开一个选择器
        Selector selector = Selector.open();

        // 把通道注册到选择器中  声明选择器监听的事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 先创建读写buffer
        ByteBuffer readBuffer = ByteBuffer.allocate(128);
        ByteBuffer writeBuffer = ByteBuffer.allocate(128);
        writeBuffer.put("hello client from 4321".getBytes());


        // 选择器管理通道的逻辑
        // 不断的获取select()的返回值  判断是否有通道要执行操作
        while (true) {
            int ready = selector.select();
            if (ready == 0) {
                continue;
            }

            // 获取到 哪些通道要执行操作
            Set<SelectionKey> set = selector.selectedKeys();
            // 遍历集合
            Iterator<SelectionKey> iterator = set.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if (key.isAcceptable()) {

                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_WRITE);

                } else if (key.isWritable()) {

                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    writeBuffer.flip();
                    socketChannel.write(writeBuffer);
                    key.interestOps(SelectionKey.OP_READ);

                } else if (key.isReadable()) {

                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    readBuffer.clear();
                    socketChannel.read(readBuffer);
                    readBuffer.flip();

                    StringBuffer stringBuffer = new StringBuffer();
                    while (readBuffer.hasRemaining()) {
                        stringBuffer.append((char) readBuffer.get());
                    }

                    System.out.println("client data : " + stringBuffer);

                } else if (key.isConnectable()) {

                }

            }
        }

    }
}
