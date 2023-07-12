package com.learning.javalearning.socket.nio2.completionhandler.server;



import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;

@Slf4j
public class Nio2CompletionHandlerEchoServer {

    public static void main(String[] args) throws IOException {
        AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(4000));
        log.info("Echo server started");

        AcceptCompletionHandler acceptCompletionHandler = new AcceptCompletionHandler(serverSocketChannel);
        serverSocketChannel.accept(null, acceptCompletionHandler);

        System.in.read();
        log.info("Echo server finished");
    }
}
