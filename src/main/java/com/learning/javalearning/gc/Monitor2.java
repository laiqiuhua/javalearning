package com.learning.javalearning.gc;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Field;

@Slf4j
public class Monitor2 extends Thread {
  ReferenceQueue<Object> queue;

  public Monitor2(ReferenceQueue<Object> queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    try {
      while (true) {
        Reference<?> ref = queue.poll();
        log.info("remove reference:{}", ref);
        if (ref != null) {
          Field field = Reference.class.getDeclaredField("referent");
          field.setAccessible(true);

          log.info("ReferenceQueue get Referent:{}", field.get(ref));
          ref.clear();
          break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}