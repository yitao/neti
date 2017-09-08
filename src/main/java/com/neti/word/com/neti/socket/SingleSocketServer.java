package com.neti.word.com.neti.socket;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by yitao on 2017/8/25.
 */
public class SingleSocketServer {

    public static void main(String[] args) throws IOException {
        int port = 10086;
        System.out.println("服务器已经启动");
        System.out.println("服务器监听端口:"+port);
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("等待客户端连接...");
        Socket socket = serverSocket.accept();
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        System.out.println("客户端已连接");
        String info = null;
        while ((info = br.readLine()) != null) {
            System.out.println("客户端：" + info);
        }
        socket.shutdownInput();//关闭输入流
        //4、获取输出流，响应客户端的请求
        OutputStream os = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(os);
        pw.write("欢迎您！");
        pw.flush();


        pw.close();
        os.close();
        br.close();
        isr.close();
        is.close();
        socket.close();
        serverSocket.close();
    }
}
