package com.learning.javalearning.lock.cyclicbarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier栅栏,是一个同步工具类，允许一组线程相互之间等待，当所有线程都达到一个屏障点后，所有线程再继续执行。
 * 例子：假设一组线程都要进行读操作，等所有线程读操作完成后才能进行接下来的操作
 * CountDownLatch与CyclicBarrier区别：
 * 1.CountDownLatch是一个或多个线程等待其他线程都完成某些事情后才开始执行；CyclicBarrier是一组线程之间相互等待，都到达一个屏障点后，所有线程再继续执行；
 * 2.CountDownLatch当线程到达countDown()方法后线程该线程还会继续运行，CyclicBarrier当线程到达await()方法后要等待所有线程都到达后才能继续运行;
 * 3.CountDownLatch不可以循环使用，而CyclicBarrier可以循环使用;
 * 4.CountDownLatch的基本操作组合是countDown()/await(),CyclicBarrier是await()
 *
 * 如果在参与者（线程）在等待的过程中，Barrier被破坏，就会抛出BrokenBarrierException。可以用isBroken()方法检测Barrier是否被破坏。
 * 如果有线程已经处于等待状态，调用reset方法会导致已经在等待的线程出现BrokenBarrierException异常。并且由于出现了BrokenBarrierException，将会导致始终无法等待。
 * 如果在等待的过程中，线程被中断，也会抛出BrokenBarrierException异常，并且这个异常会传播到其他所有的线程。
 * 如果在执行屏障操作过程中发生异常，则该异常将传播到当前线程中，其他线程会抛出BrokenBarrierException，屏障被损坏。
 * 如果超出指定的等待时间，当前线程会抛出 TimeoutException 异常，其他线程会抛出BrokenBarrierException异常。
 *
 */
public class CyclicBarrierDemo {
    static class PreTaskThread implements Runnable {

        private String task;
        private CyclicBarrier cyclicBarrier;

        public PreTaskThread(String task, CyclicBarrier cyclicBarrier) {
            this.task = task;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            // 假设总共三个关卡
            for (int i = 1; i < 4; i++) {
                try {
                    Random random = new Random();
                    Thread.sleep(random.nextInt(1000));
                    System.out.println(String.format("关卡%d的任务%s完成", i, task));
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                cyclicBarrier.reset(); // 重置屏障
            }
        }
    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            System.out.println("本关卡所有前置任务完成，开始游戏...");
        });

        new Thread(new PreTaskThread("加载地图数据", cyclicBarrier)).start();
        new Thread(new PreTaskThread("加载人物模型", cyclicBarrier)).start();
        new Thread(new PreTaskThread("加载背景音乐", cyclicBarrier)).start();
    }
}