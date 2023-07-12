package com.learning.javalearning.socket.nio.server.channel;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NioNonBlockingEchoServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        log.info("Echo server is blocking: {}", serverSocketChannel.isBlocking());

        serverSocketChannel.bind(new InetSocketAddress(4000));
        log.info("Echo server started: {}", serverSocketChannel);

        boolean active = true;
        while (active) {
            SocketChannel socketChannel = serverSocketChannel.accept(); // non-blocking
            if (socketChannel == null) {
                log.info("waiting for incoming connection...");
                TimeUnit.MILLISECONDS.sleep(5000);
            } else {
                log.info("Connection accepted: {}", socketChannel);
                socketChannel.configureBlocking(false);
                log.info("Connection is blocking: {}", socketChannel.isBlocking());

                ByteBuffer buffer = ByteBuffer.allocate(1024);
                while (true) {
                    buffer.clear();
                    int read = socketChannel.read(buffer); // non-blocking
                    log.info("Echo server read: {} byte(s)", read);
                    if (read < 0) {
                        break;
                    }

                    buffer.flip();
                    byte[] bytes = new byte[buffer.limit()];
                    buffer.get(bytes);
                    String message = new String(bytes, StandardCharsets.UTF_8);
                    log.info("Echo server received: {}", message);
                    if (message.trim().equals("bye")) {
                        active = false;
                    }

                    buffer.flip();
                    socketChannel.write(buffer); // can be non-blocking
                }

                socketChannel.close();
                log.info("Connection closed");
            }
        }

        serverSocketChannel.close();
        log.info("Echo server finished");
    }
}
