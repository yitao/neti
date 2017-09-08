package com.neti.word.com.neti.socket;

import java.io.IOException;

/**
 * Created by yitao on 2017/8/25.
 */
public class SocketClientMain2 {
    public static void main(String[] args) throws IOException {
        SocketClient client1 = new SocketClient("localhost",10086);
        client1.init();
        client1.sendMsg("hello world222222");
        client1.readMsg();
        client1.close();
    }
}
