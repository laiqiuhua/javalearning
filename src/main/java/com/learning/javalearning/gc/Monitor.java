package com.learning.javalearning.gc;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.ReferenceQueue;

@Slf4j
public class Monitor extends Thread {
    ReferenceQueue<Object> queue;

    public Monitor(ReferenceQueue<Object> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                log.info("remove reference:{}", queue.remove().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
