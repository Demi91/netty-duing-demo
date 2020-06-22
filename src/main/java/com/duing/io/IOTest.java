package com.duing.io;

import java.io.*;

public class IOTest {

    public static void main(String[] args) throws Exception {

        // 此时的相对路径  对应项目的根路径
        File file = new File("io.txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        // 写入字节数据
        String str = "hello io again again";
//        OutputStream os = new FileOutputStream(file);
//        os.write(str.getBytes());
//        os.close();

//        Writer writer = new FileWriter(file);
//        writer.write(str);
//        writer.close();

        // 读出字节数据
//        InputStream is = new FileInputStream(file);
//        byte[] byteArr = new byte[(int) file.length()];
//        int size = is.read(byteArr);
//        System.out.println("读取数据的大小：" + size
//                + ",数据内容为：" + new String(byteArr));
//
//        is.close();

//        Reader reader = new FileReader(file);
//        char[] charArr = new char[(int) file.length()];
//        int charSize = reader.read(charArr);
//        System.out.println("读取数据的大小：" + charSize
//                + ",数据内容为：" + new String(charArr));
//
//        reader.close();


        copyByBuffer("io.txt","io_new.txt");
    }

    // 拷贝文件内容
    public static void copy(String srcName, String destName) throws Exception {

        File src = new File(srcName);
        File dest = new File(destName);
        if (!dest.exists()) {
            dest.createNewFile();
        }

        InputStream is = new FileInputStream(src);
        OutputStream os = new FileOutputStream(dest);

        byte[] byteArr = new byte[1024];
        int size = 0;
        // 当读到文件尾部无数据  返回-1
        while ((size = is.read(byteArr)) != -1) {
            os.write(byteArr, 0, size);
        }

        os.flush();
        os.close();
        is.close();
    }


    // 拷贝文件内容
    public static void copyByBuffer(String srcName, String destName) throws Exception {

        File src = new File(srcName);
        File dest = new File(destName);
        if (!dest.exists()) {
            dest.createNewFile();
        }

        InputStream is = new FileInputStream(src);
        BufferedInputStream bis = new BufferedInputStream(is);

        OutputStream os = new FileOutputStream(dest);
        BufferedOutputStream bos = new BufferedOutputStream(os);

//        byte[] byteArr = new byte[1024];
        int tmp = 0;
        // 当读到文件尾部无数据  返回-1
        while ((tmp = bis.read()) != -1) {
           bos.write(tmp);
        }

        bos.close();
        os.close();

        bis.close();
        is.close();
    }
}
