package com.learning.javalearning.gc;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * @author laiqiuhua
 * @date 2023/7/11
 **/
@Slf4j
public class SoftReferenceGC {


    public static void main(String[] args) throws InterruptedException {
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        Object o = new Object() {
            @Override
            public String toString() {
                return "zhangsan";
            }
        };

        Reference<Object> softRef = new SoftReference<>(o, queue);
        new Monitor(queue).start();

        o = null;
        System.gc();
        log.info("o=null, referent:{}", softRef.get());

        byte[] bytes = new byte[3 * 1024 * 1024];
        System.gc();
        log.info("After GC, referent:{}", softRef.get());
        Thread.sleep(2000);
        System.gc();
        log.info("After GC, referent:{}", softRef.get());
    }

}
