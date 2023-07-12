package com.learning.javalearning.socket.io.client;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Slf4j
public class IoEchoClient {

    public static void main(String[] args) throws IOException {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String message;
        while ((message = stdIn.readLine()) != null) {
            Socket socket = new Socket("localhost", 4000);
            log.info("Echo client started: {}", socket);

            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            byte[] bytes = message.getBytes();
            os.write(bytes);

            int totalRead = 0;
            while (totalRead < bytes.length) {
                int read = is.read(bytes, totalRead, bytes.length - totalRead);
                if (read <= 0)
                    break;

                totalRead += read;
                log.info("Echo client read: {} byte(s)", read);
            }

            log.info("Echo client received: {}", new String(bytes, StandardCharsets.UTF_8));

            socket.close();
            log.info("Echo client disconnected");
        }
    }
}