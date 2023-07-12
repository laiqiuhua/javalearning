package com.learning.javalearning.socket.nio2.completionhandler.client;



import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

@Slf4j
class WriteCompletionHandler implements CompletionHandler<Integer, Attachment> {

    private final AsynchronousSocketChannel socketChannel;

    WriteCompletionHandler(AsynchronousSocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void completed(Integer bytesWritten, Attachment attachment) {
        log.info("Echo client wrote: {} byte(s)", bytesWritten);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ReadCompletionHandler readCompletionHandler = new ReadCompletionHandler(buffer);
        socketChannel.read(buffer, attachment, readCompletionHandler);
    }

    @Override
    public void failed(Throwable t, Attachment attachment) {
        log.error("Exception during socket writing", t);
    }
}