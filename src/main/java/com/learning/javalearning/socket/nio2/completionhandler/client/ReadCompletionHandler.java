package com.learning.javalearning.socket.nio2.completionhandler.client;



import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

@Slf4j
class ReadCompletionHandler implements CompletionHandler<Integer, Attachment> {

    private final ByteBuffer inputBuffer;

    ReadCompletionHandler(ByteBuffer inputBuffer) {
        this.inputBuffer = inputBuffer;
    }

    @Override
    public void completed(Integer bytesRead, Attachment attachment) {
        log.info("Echo client read: {} byte(s)", bytesRead);
        try {
            inputBuffer.flip();
            log.info("Echo client received: {}", StandardCharsets.UTF_8.newDecoder().decode(inputBuffer));

            attachment.getActive().set(false);
        } catch (IOException e) {
            log.error("Exception during echo processing", e);
        }
    }

    @Override
    public void failed(Throwable t, Attachment attachment) {
        log.error("Exception during socket reading", t);
    }
}