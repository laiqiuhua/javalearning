package com.learning.javalearning.socket.nio.channel;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
public class NioChannelEchoClient {

    public static void main(String[] args) throws IOException {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String message;
        while ((message = stdIn.readLine()) != null) {
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 4000));
            log.info("Echo client started: {}", socketChannel);

            ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
            socketChannel.write(buffer);
            log.info("Echo client sent: {}", message);

            int totalRead = 0;
            while (totalRead < message.getBytes().length) {
                buffer.clear();

                int read = socketChannel.read(buffer);
                log.info("Echo client read: {} byte(s)", read);
                if (read <= 0)
                    break;

                totalRead += read;

                buffer.flip();
                log.info("Echo client received: {}", StandardCharsets.UTF_8.newDecoder().decode(buffer));
            }

            socketChannel.close();
            log.info("Echo client disconnected");
        }
    }
}
