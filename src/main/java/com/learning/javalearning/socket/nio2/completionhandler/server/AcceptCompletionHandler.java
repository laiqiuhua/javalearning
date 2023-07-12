package com.learning.javalearning.socket.nio2.completionhandler.server;



import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

@Slf4j
class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Void> {

    private final AsynchronousServerSocketChannel serverSocketChannel;

    AcceptCompletionHandler(AsynchronousServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    public void completed(AsynchronousSocketChannel socketChannel, Void attachment) {
        log.info("Connection accepted: {}", socketChannel);

        serverSocketChannel.accept(null, this);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ReadCompletionHandler readCompletionHandler = new ReadCompletionHandler(socketChannel, buffer);
        socketChannel.read(buffer, null, readCompletionHandler);
    }

    @Override
    public void failed(Throwable t, Void attachment) {
        log.error("Exception during connection accepting", t);
    }
}
