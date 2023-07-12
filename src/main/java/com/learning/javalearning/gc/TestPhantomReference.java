package com.learning.javalearning.gc;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * 1）、虚引用对象，在回收器确定其指示对象可另外回收之后，被加入队列。虚引用最常见的用法是以某种可能比使用 Java 终结机制更灵活的方式来指派 pre-mortem 清除动作。
 *
 * 2）、如果垃圾回收器确定在某一特定时间点上虚引用的指示对象是虚可到达对象，那么在那时或者在以后的某一时间，它会将该引用加入队列。
 *
 * 3）、为了确保可回收的对象仍然保持原状，虚引用的指示对象不能被获取：虚引用的 get 方法总是返回 null。
 *
 * 4）、与软引用和弱引用不同，虚引用在加入队列时并没有通过垃圾回收器自动清除。通过虚引用可到达的对象将仍然保持原状，直到所有这类引用都被清除，或者它们都变得不可到达。
 * PhantomReference，即虚引用，虚引用并不会影响对象的生命周期。虚引用的作用为：跟踪垃圾回收器收集对象这一活动的情况。
 *
 * 当GC一旦发现了虚引用对象，则会将PhantomReference对象插入ReferenceQueue队列，而此时PhantomReference对象并没有被垃圾回收器回收，而是要等到ReferenceQueue被你真正的处理后才会被回收。
 *
 * 注意：PhantomReference必须要和ReferenceQueue联合使用，SoftReference和WeakReference可以选择和ReferenceQueue联合使用也可以不选择，这使他们的区别之一。
 */
public class TestPhantomReference {

        private static ReferenceQueue<Object> rq = new ReferenceQueue<>();
        public static void main(String[] args){

            Object obj = new Object();
            PhantomReference<Object> pr = new PhantomReference<>(obj, rq);
            System.out.println(pr.get());
            obj = null;
            System.gc();
            System.out.println(pr.get());
            Reference<Object> r = (Reference<Object>)rq.poll();
            if(r!=null){
                System.out.println("回收");
            }
        }
    }