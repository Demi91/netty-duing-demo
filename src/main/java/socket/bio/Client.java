package socket.bio;

import java.io.OutputStream;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws Exception{

        // 创建socket  指定ip地址和端口号
        Socket socket = new Socket("127.0.0.1",1234);

        // 获取输出流  传递数据
        OutputStream outputStream = socket.getOutputStream();

        String message = "hello socket111";

        outputStream.write(message.getBytes());

        //关闭资源
        outputStream.close();
        socket.close();
    }

}
