package com.learning.javalearning.socket.nio2.future.client;


import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

@Slf4j
public class Nio2EchoFutureClient {

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String message;
        while ((message = stdIn.readLine()) != null) {
            AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();

            socketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 1024);
//            socketChannel.setOption(StandardSocketOptions.SO_SNDBUF, 1024); // MacOS 不支持此选项
            socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);

            socketChannel.connect(new InetSocketAddress("localhost", 4000)).get();

            ByteBuffer outputBuffer = ByteBuffer.wrap(message.getBytes());
            socketChannel.write(outputBuffer).get();
            log.info("Echo client sent: {}", message);

            ByteBuffer inputBuffer = ByteBuffer.allocate(1024);
            while (socketChannel.read(inputBuffer).get() != -1) {
                inputBuffer.flip();
                CharBuffer charBuffer = StandardCharsets.UTF_8.newDecoder().decode(inputBuffer);
                log.info("Echo client received: {}", charBuffer);

                if (inputBuffer.hasRemaining()) {
                    inputBuffer.compact();
                } else {
                    inputBuffer.clear();
                    break;
                }
            }

            socketChannel.close();
        }
    }
}
