package com.learning.javalearning.threads.pool;

import java.util.concurrent.*;

/**
 * Created by Administrator
 */
public class TestMain {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool(new MyThreadFactory("test-prefix"));
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new TestRunnable(String.valueOf(i)));
        }
        executorService.shutdown();

    }

    static class TestRunnable implements Runnable {

        private String msg;

        public TestRunnable(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            System.out.println("thread name:" + Thread.currentThread().getName() + " ==== " + msg);
        }
    }
}
