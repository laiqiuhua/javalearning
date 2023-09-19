package com.learning.javalearning.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class FibonacciTest {

   static class Fibonacci extends RecursiveTask<Integer> {

        int n;

        public Fibonacci(int n) {
            this.n = n;
        }

        // 主要的实现逻辑都在compute()里
        @Override
        protected Integer compute() {
            // 这里先假设 n >= 0
            if (n <= 1) {
                return n;
            } else {
                // f(n-1)
                Fibonacci f1 = new Fibonacci(n - 1);
                f1.fork();
                // f(n-2)
                Fibonacci f2 = new Fibonacci(n - 2);
                f2.fork();
                // f(n) = f(n-1) + f(n-2)
                return f1.join() + f2.join();
            }
        }
    }

    // 普通递归，复杂度为O(2^n)
    public static int plainRecursion(int n) {
        if (n == 1 || n == 2) {
            return 1;
        } else {
            return plainRecursion(n -1) + plainRecursion(n - 2);
        }
    }

    public static void testPlain() {
        long start = System.currentTimeMillis();
        int result = plainRecursion(20);
        long end = System.currentTimeMillis();
        System.out.println("计算结果:" + result);
        System.out.printf("耗时：%d millis%n",  end -start);
    }

    public static void testForkJoin() throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        System.out.println("CPU核数：" + Runtime.getRuntime().availableProcessors());
        long start = System.currentTimeMillis();
        Fibonacci fibonacci = new Fibonacci(20);
        Future<Integer> future = forkJoinPool.submit(fibonacci);
        System.out.println(future.get());
        long end = System.currentTimeMillis();
        System.out.printf("耗时：%d millis%n", end - start);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        testPlain();
        testForkJoin();
    }


}