package com.learning.javalearning.socket.patterns.reactor.echo;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ReadEventHandler implements EventHandler {

    private final Selector demultiplexer;
    private final ByteBuffer buffer = ByteBuffer.allocate(1024);

    public ReadEventHandler(Selector demultiplexer) {
        this.demultiplexer = demultiplexer;
    }

    @Override
    public void handleEvent(SelectionKey handle) throws IOException {
        buffer.clear();
        SocketChannel socketChannel = (SocketChannel) handle.channel();
        socketChannel.read(buffer);

        buffer.flip();
        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes);
        log.info("Received message from client: " + new String(bytes, StandardCharsets.UTF_8));

        buffer.flip();
        socketChannel.register(demultiplexer, SelectionKey.OP_WRITE, buffer);
    }
}
