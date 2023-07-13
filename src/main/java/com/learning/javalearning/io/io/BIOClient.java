package com.learning.javalearning.io.io;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class BIOClient {

  public static void main(String[] args) throws IOException, InterruptedException {
    SocketChannel socketChannel = SocketChannel.open();
    InetSocketAddress address = new InetSocketAddress(2345);
    socketChannel.connect(address);

    RandomAccessFile file = new RandomAccessFile(
        BIOClient.class.getClassLoader().getResource("in.txt").getFile(), "rw");
    FileChannel channel = file.getChannel();
    channel.transferTo(0, channel.size(), socketChannel);
    channel.close();
    file.close();
    socketChannel.close();
  }
}