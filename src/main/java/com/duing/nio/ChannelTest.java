package com.duing.nio;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelTest {

    public static void main(String[] args) throws Exception {

        File file = new File("nio.txt");
        if (!file.exists()) file.createNewFile();

        FileOutputStream os = new FileOutputStream(file);
        // 通道通过stream获取
        FileChannel channel = os.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        String str = "hello nio";
        buffer.put(str.getBytes());

        // 写完成  需要调用flip方法刷新
        buffer.flip();
        channel.write(buffer);

        channel.close();
        os.close();

    }
}
