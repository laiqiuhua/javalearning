package com.learning.javalearning.threads.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author laiqiuhua
 * @date 2023/7/15
 **/
public class ThreadPoolExecutorTest {
    private static int taskCount = 1000;//任务数
    private static AtomicInteger taskCountExecuted;//实际完成任务数

    public static void main(String[] args) {
        taskCountExecuted = new AtomicInteger();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,//核心线程数
                20,//最大线程数
                5,//非核心回收超时时间
                TimeUnit.SECONDS,//超时时间单位
                new ArrayBlockingQueue<>(30),//任务队列
                new MyThreadFactory("测试线程池-")
        );
        System.out.println("总任务数：" + taskCount);
        long start = System.currentTimeMillis();
        //模拟任务提交
        for (int i = 0; i < taskCount; i++) {
            Runnable thread = () -> {
//                try {
//                    Thread.sleep(100);//模拟执行耗时
                    System.out.println(Thread.currentThread().getName() + "已执行" + taskCountExecuted.addAndGet(1) + "个任务");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            };

            //注意这里我try起来了，默认拒绝策略会报错
//            try {
                executor.execute(thread);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
        long end = System.currentTimeMillis();
        System.out.println(taskCountExecuted + "个任务已执行,总耗时：" + (end - start));
        executor.shutdown();
    }
}
