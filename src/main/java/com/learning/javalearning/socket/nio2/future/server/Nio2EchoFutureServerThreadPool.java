package com.learning.javalearning.socket.nio2.future.server;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class Nio2EchoFutureServerThreadPool {

    private static boolean active = true;

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open();

        serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 1024);
//        serverSocketChannel.setOption(StandardSocketOptions.SO_SNDBUF, 1024);  // MacOS 不支持此选项
        serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);

        serverSocketChannel.bind(new InetSocketAddress("localhost", 4000));
        log.info("Echo server started: {}", serverSocketChannel);

        ExecutorService executorService = Executors.newCachedThreadPool();

        while (active) {
            Future<AsynchronousSocketChannel> socketChannelFuture = serverSocketChannel.accept();

            AsynchronousSocketChannel socketChannel = socketChannelFuture.get();
            log.info("Connection: {}", socketChannel);

            Runnable worker = new Worker(socketChannel);
            executorService.submit(worker);
        }

        log.info("Echo server is finishing");
        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }

        serverSocketChannel.close();
        log.info("Echo server finished");
    }

    private static class Worker implements Runnable {

        private final AsynchronousSocketChannel socketChannel;

        Worker(AsynchronousSocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        @Override
        public void run() {
            try {
                log.info("Connection: {}", socketChannel);

                ByteBuffer buffer = ByteBuffer.allocate(1024);
                while (socketChannel.read(buffer).get() != -1) {
                    buffer.flip();

                    socketChannel.write(buffer).get();

                    if (buffer.hasRemaining()) {
                        buffer.compact();
                    } else {
                        buffer.clear();
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } finally {
                try {
                    socketChannel.close();
                    System.out.println("Connection finished");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
