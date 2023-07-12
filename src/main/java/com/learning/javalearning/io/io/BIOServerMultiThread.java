package com.learning.javalearning.io.io;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class BIOServerMultiThread {
  private static final Logger LOGGER = LoggerFactory.getLogger(BIOServerMultiThread.class);
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
        new Thread( () -> {
          try{
            InputStream inputstream = socket.getInputStream();
            log.info("Received message {}", IOUtils.toString(inputstream));
            IOUtils.closeQuietly(inputstream);
          } catch (IOException ex) {
            log.error("Read message failed", ex);
          }
        }).start();
      }
    } catch(IOException ex) {
      IOUtils.closeQuietly(serverSocket);
      log.error("Accept connection failed", ex);
    }
  }
}