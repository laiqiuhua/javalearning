package com.learning.javalearning.socket.io.server;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class IoEchoThreadPoolServer {

    private static final AtomicBoolean active = new AtomicBoolean(true);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4000);
        log.info("Echo server started: {}", serverSocket);

        ExecutorService executorService = Executors.newCachedThreadPool();

        while (active.get()) {
            Socket socket = serverSocket.accept(); // blocking
            executorService.submit(new Worker(socket));
        }

        log.info("Echo server is finishing");
        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }

        serverSocket.close();
        log.info("Echo server finished");
    }

    private static class Worker implements Runnable {

        private final Socket socket;

        Worker(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                log.info("Connection accepted: {}", socket);

                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();

                int read;
                byte[] bytes = new byte[1024];
                while ((read = is.read(bytes)) != -1) { // blocking
                    log.info("Echo server read: {} byte(s)", read);

                    String message = new String(bytes, 0, read, StandardCharsets.UTF_8);
                    log.info("Echo server received: {}", message);
                    if (message.trim().equals("bye")) {
                        active.set(false);
                    }

                    os.write(bytes, 0, read); // blocking
                }
            } catch (IOException e) {
                log.error("Exception during socket reading/writing", e);
            } finally {
                try {
                    socket.close();
                    log.info("Connection closed");
                } catch (IOException e) {
                    log.error("Exception during socket closing", e);
                }
            }
        }
    }
}
