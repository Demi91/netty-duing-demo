package socket.nio;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {

    public static void main(String[] args) throws Exception {

        // 创建一个服务端管道  open是打开通道的意思
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 绑定ip地址和端口号
        SocketAddress address = new InetSocketAddress("127.0.0.1", 4321);
        serverSocketChannel.socket().bind(address);

        // 接收客户端的连接
        SocketChannel socketChannel = serverSocketChannel.accept();

        // 读写数据都要通过buffer
        ByteBuffer writeBuffer = ByteBuffer.allocate(128);
        writeBuffer.put("hello client from 4321".getBytes());

        // 写完成  调用flip
        writeBuffer.flip();
        // 处理完buffer  传入通道中
        socketChannel.write(writeBuffer);

        // 创建读数据的buffer
        ByteBuffer readBuffer = ByteBuffer.allocate(128);
        socketChannel.read(readBuffer);

        // 读完成
        readBuffer.flip();
        // 判断是否还有数据
        StringBuffer stringBuffer = new StringBuffer();
        while (readBuffer.hasRemaining()) {
            stringBuffer.append((char) readBuffer.get());
        }

        System.out.println("client data : " + stringBuffer);

        socketChannel.close();
        serverSocketChannel.close();


    }
}
