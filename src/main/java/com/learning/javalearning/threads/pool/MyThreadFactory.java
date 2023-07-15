package com.learning.javalearning.threads.pool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator
 */
public class MyThreadFactory implements ThreadFactory {

    private final AtomicInteger atomicInteger = new AtomicInteger();

    private final String namePrefix;

    public MyThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        atomicInteger.incrementAndGet();
        return new Thread(r,
                namePrefix + atomicInteger.getAndIncrement());
    }
}

