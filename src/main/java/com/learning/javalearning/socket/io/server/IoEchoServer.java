package com.learning.javalearning.socket.io.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Slf4j
public class IoEchoServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4000);
        log.info("Echo server started: {}", serverSocket);

        boolean active = true;
        while (active) {
            Socket socket = serverSocket.accept(); // blocking
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
                    active = false;
                }

                os.write(bytes, 0, read); // blocking
            }

            socket.close();
            log.info("Connection closed");
        }

        serverSocket.close();
        log.info("Echo server finished");
    }
}
