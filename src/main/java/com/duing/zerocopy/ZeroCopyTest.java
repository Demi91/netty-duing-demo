package com.duing.zerocopy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class ZeroCopyTest {

    public static void main(String[] args) throws Exception {

//        copyByMMap("nio.txt", "nio_new.txt");
        copyBySendFile("nio.txt", "nio_new.txt");

    }


    public static void copyByMMap(String sourceName, String destName)
            throws Exception {
        File source = new File(sourceName);
        File dest = new File(destName);
        if (!dest.exists()) dest.createNewFile();

        FileInputStream fis = new FileInputStream(source);
        FileChannel inChannel = fis.getChannel();

        FileOutputStream fos = new FileOutputStream(dest);
        FileChannel outChannel = fos.getChannel();


        // ByteBuffer子类  对应于mmap内存映射的拷贝方式
        MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, source.length());
        outChannel.write(buffer);
        buffer.clear();

        inChannel.close();
        fis.close();

        outChannel.close();
        fos.close();

    }


    // 效率更高
    public static void copyBySendFile(String sourceName, String destName)
            throws Exception {

        File source = new File(sourceName);
        File dest = new File(destName);
        if (!dest.exists()) dest.createNewFile();

        FileInputStream fis = new FileInputStream(source);
        FileChannel inChannel = fis.getChannel();

        FileOutputStream fos = new FileOutputStream(dest);
        FileChannel outChannel = fos.getChannel();

        // 通过transferTo 直接从A通道搬运数据到B通道
        inChannel.transferTo(0,inChannel.size(),outChannel);

        inChannel.close();
        fis.close();

        outChannel.close();
        fos.close();

    }
}
