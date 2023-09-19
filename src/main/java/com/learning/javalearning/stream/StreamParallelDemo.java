package com.learning.javalearning.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StreamParallelDemo {
    public static void main(String[] args) {
        System.out.printf("本计算机的核数：%d%n", Runtime.getRuntime().availableProcessors());

        // 产生100w个随机数(1 ~ 100)，组成列表
        Random random = new Random();
        List<Integer> list = new ArrayList<>(1000_0000);

        for (int i = 0; i < 1000_0000; i++) {
            list.add(random.nextInt(100));
        }

        long prevTime = getCurrentTime();
        list.stream().reduce(Integer::sum).ifPresent(System.out::println);
        System.out.printf("单线程计算耗时：%d%n", getCurrentTime() - prevTime);

        prevTime = getCurrentTime();
        list.stream().parallel().reduce(Integer::sum).ifPresent(System.out::println);
        System.out.printf("多线程计算耗时：%d%n", getCurrentTime() - prevTime);

    }

    private static long getCurrentTime() {
        return System.currentTimeMillis();
    }
}