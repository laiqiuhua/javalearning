package com.learning.javalearning.socket.nio2.completionhandler.server;



import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

@Slf4j
class WriteCompletionHandler implements CompletionHandler<Integer, Void> {

    private final AsynchronousSocketChannel socketChannel;

    WriteCompletionHandler(AsynchronousSocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void completed(Integer bytesWritten, Void attachment) {
        log.info("Echo server wrote: {} byte(s)", bytesWritten);

        try {
            socketChannel.close();
            log.info("Connection closed");
        } catch (IOException e) {
            log.error("Exception during socket closing", e);
        }
    }

    @Override
    public void failed(Throwable t, Void attachment) {
        log.error("Exception during socket writing", t);
    }
}
