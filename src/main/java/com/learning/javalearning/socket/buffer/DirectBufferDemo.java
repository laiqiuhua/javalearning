package com.learning.javalearning.socket.buffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author laiqiuhua
 * @date 2023/7/12
 **/
public class DirectBufferDemo {

    public static void main(String[] args) throws IOException {
        String filePath = Thread.currentThread().getContextClassLoader().getResource("in.txt").getFile();
        FileInputStream inputStream = new FileInputStream(filePath);
        FileChannel fileInChannel = inputStream.getChannel();

        String outPath = Thread.currentThread().getContextClassLoader().getResource("out.txt").getFile();
        FileOutputStream outputStream = new FileOutputStream(outPath);
        FileChannel fileOutChannel1 = outputStream.getChannel();

        //  使用 allocateDirect , 而不是 allocate
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        while (true) {
            buffer.clear();
            int r = fileInChannel.read(buffer);
            if (r == -1) {
                break;
            }
            buffer.flip();
            fileOutChannel1.write(buffer);
        }
        fileInChannel.close();
        fileOutChannel1.close();
    }
}
