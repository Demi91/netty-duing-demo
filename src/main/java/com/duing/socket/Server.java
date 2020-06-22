package com.duing.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) throws Exception {

        // 用线程池创建线程
        //  可缓冲的线程池  如果当前的线程数量超过需要使用的线程数 回收（空闲线程）
        //    当需要使用线程时  判断是否有可用线程
        //        如果有  直接复用   如果没有  自动创建
        //    此线程池可以无限大
        ExecutorService threadPool = Executors.newCachedThreadPool();

        // 设定端口号
        ServerSocket serverSocket = new ServerSocket(1234);

        // 不断轮询是否有客户端的连接
//        for (;;){}
        while (true) {
            // 等待客户端的连接   是阻塞的接受方式
            // 增加final修饰符  代表不可变  确保线程安全
            final Socket socket = serverSocket.accept();
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    handler(socket);
                }
            });
        }

    }


    // 和客户端通信的处理逻辑
    private static void handler(Socket socket) {
        System.out.println(Thread.currentThread().getId());
        System.out.println(Thread.currentThread().getName());

        // 接受客户端传递的数据
        byte[] bytes = new byte[1024];

        try {
            InputStream is = socket.getInputStream();
            while (true) {
                int read = is.read(bytes);
                // 读完成  跳出循环
                if (read == -1) break;
                System.out.println(new String(bytes, 0, read));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
