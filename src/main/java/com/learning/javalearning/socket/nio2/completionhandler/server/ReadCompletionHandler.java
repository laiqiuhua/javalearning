package com.learning.javalearning.socket.nio2.completionhandler.server;



import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

@Slf4j
class ReadCompletionHandler implements CompletionHandler<Integer, Void> {

    private final AsynchronousSocketChannel socketChannel;
    private final ByteBuffer buffer;

    ReadCompletionHandler(AsynchronousSocketChannel socketChannel, ByteBuffer buffer) {
        this.socketChannel = socketChannel;
        this.buffer = buffer;
    }

    @Override
    public void completed(Integer bytesRead, Void attachment) {
        log.info("Echo server read: {} byte(s)", bytesRead);

        buffer.flip();
        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes);
        String message = new String(bytes, StandardCharsets.UTF_8);
        log.info("Echo server received: {}", message);

        WriteCompletionHandler writeCompletionHandler = new WriteCompletionHandler(socketChannel);
        buffer.flip();
        socketChannel.write(buffer, null, writeCompletionHandler);
    }

    @Override
    public void failed(Throwable t, Void attachment) {
        log.error("Exception during socket reading", t);
    }
}
