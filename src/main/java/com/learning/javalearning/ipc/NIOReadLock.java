package com.learning.javalearning.ipc;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class NIOReadLock {
	private static RandomAccessFile raf;
 
	public static void main(String[] args) throws Exception {  
		
        raf = new RandomAccessFile("swap.nio", "rw");
        FileChannel fc = raf.getChannel();
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0, 1024);
        FileLock flock=null;
       
        for(int i=0;i<26;i++){   
        	flock=fc.lock();//上锁
            System.out.println( System.currentTimeMillis() +  ":read:" + (char)mbb.get(i));             
            flock.release();//释放锁
            Thread.sleep(3000);            
        }  
    }  
}

