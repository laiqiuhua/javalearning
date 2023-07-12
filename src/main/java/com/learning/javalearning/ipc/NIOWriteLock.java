package com.learning.javalearning.ipc;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class NIOWriteLock {
	private static RandomAccessFile raf;
	public static void main(String[] args) throws Exception {  
		//建立文件和内存的映射，即时双向同步
		raf = new RandomAccessFile("swap.nio", "rw");
        FileChannel fc = raf.getChannel();
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0, 1024);
        FileLock flock=null;
        //阻塞方法一：非阻塞独占锁，当文件锁不可用时，tryLock()会得到null值
        //do {
        //	flock=fc.tryLock();
        //} while(null == flock);
        //阻塞方法二：非阻塞共享锁，当文件锁不可用时，tryLock()会得到null值
        //fc.tryLock(0L, Long.MAX_VALUE, true);
        //阻塞方法三：阻塞共享锁，有写操作会报异常
        //flock = fc.lock(0L, Long.MAX_VALUE, true);
        
        for(int i=65;i<91;i++){ 
        	//阻塞方法四：阻塞独占锁，当文件锁不可用时，当前进程会被挂起    
        	flock=fc.lock();
        	System.out.println(System.currentTimeMillis() +  ":write:" + (char)i); 
            mbb.put(i-65,(byte)i);//从文件第一个字节位置开始写入数据  
            flock.release();//释放锁
            Thread.sleep(1000);
        }
        
    }  
}
