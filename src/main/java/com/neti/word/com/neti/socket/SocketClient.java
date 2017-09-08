package com.neti.word.com.neti.socket;

import java.io.*;
import java.net.Socket;

/**
 * Created by yitao on 2017/8/25.
 */
public class SocketClient {
    private String address;
    private int port;

    private Socket socket;
    OutputStream os;
    PrintWriter pw;
    InputStream is;
    BufferedReader br;

    public SocketClient(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void init() throws IOException {
        //客户端
        //1、创建客户端Socket，指定服务器地址和端口
        socket = new Socket(address, port);
        //2、获取输出流，向服务器端发送信息
        os = socket.getOutputStream();//字节输出流
        pw = new PrintWriter(os);//将输出流包装成打印流

    }

    public void sendMsg(String msg) throws IOException{
        pw.write(msg);
        pw.flush();
        socket.shutdownOutput();
    }

    public void readMsg() throws IOException{
        //3、获取输入流，并读取服务器端的响应信息
        is = socket.getInputStream();
        br = new BufferedReader(new InputStreamReader(is));
        String info = null;
        while ((info = br.readLine()) != null) {
            System.out.println("我是客户端，服务器说：" + info);
        }
    }

    public void close() throws IOException {
        //4、关闭资源
        br.close();
        is.close();
        pw.close();
        os.close();
        socket.close();
    }

}
