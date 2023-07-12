package com.learning.javalearning.socket.nio2.completionhandler.client;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

@Slf4j
class AcceptCompletionHandler implements CompletionHandler<Void, Attachment> {

    private final AsynchronousSocketChannel socketChannel;

    AcceptCompletionHandler(AsynchronousSocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void completed(Void result, Attachment attachment) {
        log.info("Connection accepted: {}", socketChannel);

        String message = attachment.getMessage();
        log.info("Echo client sent: {}", message);

        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8));
        WriteCompletionHandler writeCompletionHandler = new WriteCompletionHandler(socketChannel);
        socketChannel.write(buffer, attachment, writeCompletionHandler);
    }

    @Override
    public void failed(Throwable t, Attachment attachment) {
        log.error("Exception during connection accepting", t);
    }
}
