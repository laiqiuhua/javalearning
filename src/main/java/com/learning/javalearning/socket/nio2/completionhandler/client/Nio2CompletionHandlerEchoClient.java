package com.learning.javalearning.socket.nio2.completionhandler.client;



import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousSocketChannel;

@Slf4j
public class Nio2CompletionHandlerEchoClient {

    public static void main(String[] args) throws IOException {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String message;
        while ((message = stdIn.readLine()) != null) {
            AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();

            socketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 1024);
            socketChannel.setOption(StandardSocketOptions.SO_SNDBUF, 1024);
            socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);

            Attachment attachment = new Attachment(message, true);
            AcceptCompletionHandler acceptCompletionHandler = new AcceptCompletionHandler(socketChannel);
            socketChannel.connect(new InetSocketAddress("localhost", 4000), attachment, acceptCompletionHandler);

            // 读取完数据,会设置为false
            while (attachment.getActive().get()) {
            }

            socketChannel.close();
            log.info("Echo client finished");
        }
    }
}