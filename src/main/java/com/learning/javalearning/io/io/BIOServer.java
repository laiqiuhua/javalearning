package com.learning.javalearning.io.io;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Slf4j
public class BIOServer {

  public static void main(String[] args) {
    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket();
      serverSocket.bind(new InetSocketAddress(2345));
    } catch (IOException ex) {
      log.error("Listen failed", ex);
      return;
    }
    try{
      while(true) {
        Socket socket = serverSocket.accept();
        InputStream inputstream = socket.getInputStream();
        log.info("Received message {}", StreamUtils.copyToString(inputstream, StandardCharsets.UTF_8));
        IOUtils.closeQuietly(inputstream);
      }
    } catch(IOException ex) {
      IOUtils.closeQuietly(serverSocket);
      log.error("Read message failed", ex);
    }
  }
}