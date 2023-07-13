package com.learning.javalearning.socket.buffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author laiqiuhua
 * @date 2023/7/12
 **/
public class MappedByteBufferDemo {

    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(Thread.currentThread().getContextClassLoader().getResource("in.txt").getFile(), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 1024);
        mappedByteBuffer.put(0, (byte) 97);
        mappedByteBuffer.put(1023, (byte) 122);
        fileChannel.close();
    }
}
