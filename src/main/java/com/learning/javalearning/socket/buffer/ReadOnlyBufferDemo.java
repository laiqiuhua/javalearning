package com.learning.javalearning.socket.buffer;

import java.nio.ByteBuffer;

/**
 * @author laiqiuhua
 * @date 2023/7/12
 **/
public class ReadOnlyBufferDemo {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        // 放入数据
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        // 创建一个只读缓冲区
        ByteBuffer readOnlyBuf = buffer.asReadOnlyBuffer();
        for (int i = 0; i < buffer.capacity(); i++) {
            byte b = buffer.get(i);
            b *= 10;
            buffer.put(i, b);
        }

        readOnlyBuf.position(0);
        readOnlyBuf.limit(readOnlyBuf.capacity());
        while (readOnlyBuf.remaining() > 0) {
            System.out.println(readOnlyBuf.get());
        }

    }
}
