package com.learning.javalearning.gc;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * 1）、弱引用对象，它们并不禁止其指示对象变得可终结，并被终结，然后被回收。弱引用最常用于实现规范化的映射。
 *
 * 2）、假定垃圾回收器确定在某一时间点上某个对象是弱可到达对象。这时，它将自动清除针对此对象的所有弱引用，以及通过强引用链和软引用，可以从其到达该对象的针对任何其他弱可到达对象的所有弱引用。同时它将声明所有以前的弱可到达对象为可终结的。在同一时间或晚些时候，它将那些已经向引用队列注册的新清除的弱引用加入队列。
 */
public class TestWeakReference {
        private static final ReferenceQueue<Object> rq = new ReferenceQueue<Object>();
        public static void main(String[] args) {
            Object obj = new Object();
            WeakReference<Object> wr = new WeakReference<>(obj,rq);
            System.out.println(wr.get()!=null);
            obj = null;
            System.gc();
            System.out.println(wr.get()!=null);//false，这是因为WeakReference被回收
        }

    }