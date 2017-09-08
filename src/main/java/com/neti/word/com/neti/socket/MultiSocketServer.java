package com.neti.word.com.neti.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by yitao on 2017/8/25.
 */
public class MultiSocketServer {

    public static void main(String[] args) throws IOException {
        int port = 10086;
        System.out.println("服务器已经启动");
        System.out.println("服务器监听端口:" + port);
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("等待客户端连接...");
        int count = 0;//记录客户端的数量
        Socket socket = null;
        while (true) {
            socket = serverSocket.accept();
            ServerThread serverThread = new ServerThread(socket);
            serverThread.start();
            count++;
            System.out.println("客户端连接的数量：" + count);
        }
    }
}

class ServerThread extends Thread {
    Socket socket = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("客户端连接");
        System.out.println(socket.getLocalAddress().toString());
    }
}
