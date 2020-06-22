package com.duing.socket;

import java.io.OutputStream;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("127.0.0.1", 1234);

        OutputStream os = socket.getOutputStream();
        String message = "hello socket";

        os.write(message.getBytes());

        os.close();
        socket.close();
    }
}
