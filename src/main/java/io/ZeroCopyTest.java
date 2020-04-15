package io;

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

    public static void copyByMMap(String sourceFile, String targetFile) throws Exception {
        File source = new File(sourceFile);
        File target = new File(targetFile);
        if (!target.exists()) target.createNewFile();

        FileInputStream fis = new FileInputStream(sourceFile);
        FileChannel inChannel = fis.getChannel();

        FileOutputStream fos = new FileOutputStream(targetFile);
        FileChannel outChannel = fos.getChannel();

        MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, source.length());
        outChannel.write(buffer);
        buffer.clear();

        inChannel.close();
        fis.close();
        outChannel.close();
        fos.close();
    }


    public static void copyBySendFile(String sourceFile, String targetFile) throws Exception {
        File target = new File(targetFile);
        if (!target.exists()) target.createNewFile();

        FileInputStream fis = new FileInputStream(sourceFile);
        FileChannel inChannel = fis.getChannel();

        FileOutputStream fos = new FileOutputStream(targetFile);
        FileChannel outChannel = fos.getChannel();

        inChannel.transferTo(0,inChannel.size(),outChannel);

        inChannel.close();
        fis.close();
        outChannel.close();
        fos.close();
    }

}
