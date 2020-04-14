package socket.nio;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {

    public static void main(String[] args) throws Exception {

        // 创建一个客户端管道  open是打开通道的意思
        SocketChannel socketChannel = SocketChannel.open();

        // 连接到服务器
        SocketAddress address = new InetSocketAddress("127.0.0.1", 4321);
        socketChannel.connect(address);

        // 先写后读去处理
        // 写数据传入服务端
        // 读写数据都要通过buffer
        ByteBuffer writeBuffer = ByteBuffer.allocate(128);
        writeBuffer.put("hello 4321 server from client".getBytes());

        // 写完成  调用flip
        writeBuffer.flip();
        // 处理完buffer  传入通道中
        socketChannel.write(writeBuffer);


        // 读从服务端传输的数据
        ByteBuffer readBuffer = ByteBuffer.allocate(128);
        socketChannel.read(readBuffer);

        // 读完成
        readBuffer.flip();
        // 判断是否还有数据
        StringBuffer stringBuffer = new StringBuffer();
        while (readBuffer.hasRemaining()) {
            stringBuffer.append((char) readBuffer.get());
        }

        System.out.println("server data : " + stringBuffer);

        socketChannel.close();

    }
}
