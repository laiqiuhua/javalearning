package com.learning.javalearning.io.nio.multiplexing;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * 多路复用 server
 */
@Slf4j
public class MultiplexingIOServer {

  public static void main(String[] args) throws IOException {
    Selector selector = Selector.open();
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.configureBlocking(false);
    serverSocketChannel.bind(new InetSocketAddress(2345));
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

    int coreNum = Runtime.getRuntime().availableProcessors();
    Processor[] processors = new Processor[coreNum];
    for (int i = 0; i < processors.length; i++) {
      processors[i] = new Processor();
    }

    int index = 0;
    while (selector.select() > 0) {
      Set<SelectionKey> keys = selector.selectedKeys();
      for (SelectionKey key : keys) {
        keys.remove(key);
        if (key.isAcceptable()) {
          ServerSocketChannel acceptServerSocketChannel = (ServerSocketChannel) key.channel();
          SocketChannel socketChannel = acceptServerSocketChannel.accept();
          socketChannel.configureBlocking(false);
          log.info("Accept request from {}", socketChannel.getRemoteAddress());
          Processor processor = processors[((index++) % coreNum)];
          processor.addChannel(socketChannel);
          processor.wakeup();
        }
      }
    }
  }
}