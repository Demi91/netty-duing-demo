package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class NIOTest {


    public static void main(String[] args) throws Exception {


    }



    public static void testBuffer() throws Exception{
        CharBuffer buffer = CharBuffer.allocate(8);
        System.out.println("capacity: "+buffer.capacity());
        System.out.println("limit: "+buffer.limit());
        System.out.println("position：" +buffer.position());


        buffer.put('a');
        buffer.put('b');
        System.out.println("=====加入a&b");
        System.out.println("position：" +buffer.position());

        buffer.flip();
        System.out.println("=====执行flip");
        System.out.println("limit: "+buffer.limit());
        System.out.println("position：" +buffer.position());

        System.out.println("=====获取第一个元素：" +buffer.get());
        System.out.println("position：" +buffer.position());


        buffer.clear();
        System.out.println("=====执行clear");
        System.out.println("limit: "+buffer.limit());
        System.out.println("position：" +buffer.position());

        // 执行clear后  对象依然存在  清空的只是索引位置
        System.out.println("=====获取第二个元素：" +buffer.get(1));
        System.out.println("position：" +buffer.position());
    }

    public static void test() throws Exception{
        File file = new File("nio.txt");
        file.createNewFile();

        FileOutputStream fos = new FileOutputStream(file);
        // 通道通过stream获取
        FileChannel channel = fos.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        String str = "hello nio";
        buffer.put(str.getBytes());
        // 写完数据  需要调用flip方法
        buffer.flip();
        channel.write(buffer);

        channel.close();
        fos.close();
    }
}
