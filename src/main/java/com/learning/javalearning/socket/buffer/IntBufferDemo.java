package com.learning.javalearning.socket.buffer;

import java.nio.IntBuffer;

public class IntBufferDemo {

	public static void main(String[] args) {
		IntBuffer buffer=IntBuffer.allocate(10);
		
		System.out.println("写入buffer之前的:");
		System.out.println("position="+buffer.position()+",limit="+buffer.limit()+",capacity="+buffer.capacity());

		int[] temp= {1,2,5,9};
		buffer.put(1);
		buffer.put(temp);
		System.out.println("写入buffer之后的：");
		System.out.println("position="+buffer.position()+",limit="+buffer.limit()+",capacity="+buffer.capacity());

		buffer.flip();//重设缓冲区,读取前必用的方法
		System.out.println("重设buffer之后的：");
		System.out.println("position="+buffer.position()+",limit="+buffer.limit()+",capacity="+buffer.capacity());
		System.out.println("缓冲区内容：");
		while(buffer.hasRemaining()){
			int number=buffer.get();
			System.out.print(number+"、");
		}
		System.out.println();

		System.out.println("读取buffer之后的：");
		System.out.println("position="+buffer.position()+",limit="+buffer.limit()+",capacity="+buffer.capacity());

		buffer.rewind();
		System.out.println("rewind buffer之后的：");
		System.out.println("position="+buffer.position()+",limit="+buffer.limit()+",capacity="+buffer.capacity());
		System.out.println("rewind重新读取缓冲区内容：");
		while(buffer.hasRemaining()){
			int number=buffer.get();
			System.out.print(number+"、");
		}
		System.out.println();

		buffer.rewind();
		buffer.get();
		buffer.get();
		buffer.get();
		buffer.compact();
		System.out.println("compact buffer之后的：");
		System.out.println("position="+buffer.position()+",limit="+buffer.limit()+",capacity="+buffer.capacity());
		buffer.put(new int[]{7,8,9});
		System.out.println("重新读取缓冲区内容：");
		buffer.flip();
		while(buffer.hasRemaining()){
			int number=buffer.get();
			System.out.print(number+"、");
		}
		System.out.println();

		buffer.clear();
		System.out.println("clear buffer之后的：");
		System.out.println("position="+buffer.position()+",limit="+buffer.limit()+",capacity="+buffer.capacity());
		buffer.put(new int[]{1,2,3});
		System.out.println("重新读取缓冲区内容：");
		buffer.flip();
		while(buffer.hasRemaining()){
			int number=buffer.get();
			System.out.print(number+"、");
		}
		System.out.println();

	}

}
