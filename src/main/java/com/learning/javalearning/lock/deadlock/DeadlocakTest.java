package com.learning.javalearning.lock.deadlock;

import java.util.concurrent.TimeUnit;

/**
 * 记住，死锁检测的开销可能会很大，你需要用你的程序来测试一下你是否真的需要死锁检测以及多久检测一次。
 * 我建议死锁检测的时间间隔至少为几分钟，因为更加频繁的检测并没有太大的意义，原因是我们并没有一个复原计划，
 * 我们能做的只是调试和处理错误或者重启程序并祈祷不会再次发生死锁。如果你有关于解决死锁问题的好建议或者关于这个解决方案的疑问，
 * 请在下面留言
 * @Author qhlai
 * @email qhlai@gizwits.com
 */
public class DeadlocakTest {

    public static void main(String[] args) {
        DeadlockDetector deadlockDetector = new DeadlockDetector(new DeadlockConsoleHandler(), 5, TimeUnit.SECONDS);
        deadlockDetector.start();

        final Object lock1 = new Object();
        final Object lock2 = new Object();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock1) {
                    System.out.println("Thread1 acquired lock1");
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException ignore) {
                    }
                    synchronized (lock2) {
                        System.out.println("Thread1 acquired lock2");
                    }
                }
            }

        });
        thread1.start();

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock2) {
                    System.out.println("Thread2 acquired lock2");
                    synchronized (lock1) {
                        System.out.println("Thread2 acquired lock1");
                    }
                }
            }
        });
        thread2.start();
    }
}
