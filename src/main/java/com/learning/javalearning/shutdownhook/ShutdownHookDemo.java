package com.learning.javalearning.shutdownhook;


import java.util.concurrent.TimeUnit;

/**
 * Runtime.addShutdownHook解释
 * 如果你想在jvm关闭的时候进行内存清理、对象销毁等操作，或者仅仅想起个线程然后这个线程不会退出，你可以使用Runtime.addShutdownHook。
 *
 * 这个方法的作用就是在JVM中增加一个关闭的钩子。当程序正常退出、系统调用 System.exit方法或者虚拟机被关闭时才会执行系统中已经设置的所有钩子，当系统执行完这些钩子后，JVM才会关闭。所谓钩子，就是一个已初始化但并不启动的线程。JVM退出通常通过两种事件。
 *
 * 程序正常退出，例如最后一个非守护进程退出、使用System.exit()退出等
 * 程序异常退出，例如使用Ctrl+C触发的中断、用户退出或系统关闭等系统事件等 该方法的说明如下，详细说明参见官方文档。
 */
public class ShutdownHookDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread clearHook = new Thread() {
            public void run() {
                System.out.println("Run clearHook...");
            }
        };
        Runtime.getRuntime().addShutdownHook(clearHook);
        Runnable task1 = new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Run task1...");
            }
        };
        Runnable task2 = new Thread() {
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Run task2...");
            }
        };
        task1.run();
        task2.run();
        TimeUnit.SECONDS.sleep(10000);
    }
}