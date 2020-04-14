package socket.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) throws Exception {

        // 线程池创建线程
        //   可缓冲的线程池  如果当前的线程超过需要使用的线程数  会进行回收（空闲线程）
        //   如果有可用线程  做到复用  如果没有可用线程   自动创建
        //   此线程池大小是无限大
        ExecutorService threadPool = Executors.newCachedThreadPool();

        // 构造参数  是提供可连接的端口号
        ServerSocket serverSocket = new ServerSocket(1234);

        // 不断轮询是否有客户端的连接
        while (true) {
            // 等待客户端的连接  阻塞的接受方式
            // 增加final修饰符  代表socket是不可变的  才能做到线程安全
            final Socket socket = serverSocket.accept();
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    //和客户端通信的处理逻辑
                    handler(socket);
                }
            });
        }


    }

    private static void handler(Socket socket) {

        try {

            System.out.println(Thread.currentThread().getId());
            System.out.println(Thread.currentThread().getName());

            // 使用输入流  接受客户端传递的数据
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();


            // 循环读取客户端发送的数据
            while (true) {
                int read = inputStream.read(bytes);
                // 如果读取完成  跳出循环
                if (read == -1) {
                    break;
                }
                // 打印
                System.out.println(new String(bytes, 0, read));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
