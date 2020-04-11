package io;

import java.io.*;

public class IOTest {

    public static void main(String[] args) throws Exception {

        // 默认生成在项目路径下
//        File file = new File("io.txt");
//        try {
//            file.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // 可以打印绝对路径
//        System.out.println(file.getAbsolutePath());


        // 字节流
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream("io.txt");
//            // read方法  进行读操作   返回单个字节数据 可以直接转成int类型
//            // read方法提供缓冲参数  buffer
//
//            byte[] buffer = new byte[1024];
//            int hasRead = 0;
//            // 只要有数据  就可以读取  直到返回-1
//            while ((hasRead = fis.read(buffer)) != -1) {
//                System.out.println(new String(buffer, 0, hasRead));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            // 不要忘记最终要关闭
//            try {
//                fis.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }


        // 字符流
        // java7在关闭IO流的方式上进行了优化   实现AutoCloseable接口进行自动关闭
//        try (FileReader fr = new FileReader("io.txt")) {
//            char[] charBuffer = new char[1024];
//            int cRead = 0;
//            while ((cRead = fr.read(charBuffer)) != -1) {
//                System.out.println(new String(charBuffer, 0, cRead));
//            }
//        }

        copy("io.txt","io_new.txt");

    }


    public static void copy(String sourceFile, String targetFile) throws Exception {

        FileInputStream fis = new FileInputStream(sourceFile);
        BufferedInputStream bis = new BufferedInputStream(fis);

        File file = new File(targetFile);
        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(targetFile);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        int tmp;
        while ((tmp = bis.read()) != -1) {
            bos.write(tmp);
            System.out.print((char)tmp);
        }

        bos.close();
        fos.close();

        bos.close();
        fis.close();

    }
}
