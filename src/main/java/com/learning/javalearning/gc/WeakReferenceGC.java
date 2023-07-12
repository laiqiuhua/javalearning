package com.learning.javalearning.gc;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * @author laiqiuhua
 * @date 2023/7/11
 **/
@Slf4j
public class WeakReferenceGC {


    public static void main(String[] args) throws InterruptedException {
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        Object o = new Object() {
            @Override
            public String toString() {
                return "zhangsan";
            }
        };

        Reference<Object> ref = new WeakReference<>(o, queue);
        new Monitor(queue).start();

        o = null;
        log.info("Before GC, referent:{}", ref.get());
        System.gc();
        log.info("After GC, referent:{}", ref.get());
    }

}
