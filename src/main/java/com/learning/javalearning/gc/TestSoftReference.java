package com.learning.javalearning.gc;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * 1）、软引用对象，在响应内存需要时，由垃圾回收器决定是否清除此对象。软引用对象最常用于实现内存敏感的缓存。
 * <p>
 * 2）、假定垃圾回收器确定在某一时间点某个对象是软可到达对象。这时，它可以选择自动清除针对该对象的所有软引用，以及通过强引用链从其可以到达该对象的针对任何其他软可到达对象的所有软引用。在同一时间或晚些时候，它会将那些已经向引用队列注册的新清除的软引用加入队列。
 * <p>
 * 3）、软可到达对象的所有软引用都要保证在虚拟机抛出 OutOfMemoryError 之前已经被清除。否则，清除软引用的时间或者清除不同对象的一组此类引用的顺序将不受任何约束。然而，虚拟机实现不鼓励清除最近访问或使用过的软引用。
 * <p>
 * 4）、此类的直接实例可用于实现简单缓存；该类或其派生的子类还可用于更大型的数据结构，以实现更复杂的缓存。只要软引用的指示对象是强可到达对象，即正在实际使用的对象，就不会清除软引用。例如，通过保持最近使用的项的强指示对象，并由垃圾回收器决定是否放弃剩余的项，复杂的缓存可以防止放弃最近使用的项。
 * <p>
 * 总结：SoftReference是软引用，只有在JVM在抛OOM异常时才会回收。其它情况下不会回收。
 */
public class TestSoftReference {
    private static final ReferenceQueue<Object> rq = new ReferenceQueue<>();

    public static void main(String[] args) {
        System.out.println("-------gc1----------");
        gc1();
        System.out.println("-------gc2----------");
        gc2();

    }


    public static void gc1() {
        Object obj = new Object();
        SoftReference<Object> sf = new SoftReference<>(obj, rq);
        System.out.println(sf.get());
        System.gc();
        obj = null;
        System.out.println(sf.get());

    }


    public static void gc2() {
        Object obj = new Object();
        SoftReference<Object> sf = new SoftReference<>(obj);
        System.out.println(sf.get());
        System.gc();
        obj = null;
        System.out.println(sf.get());

    }
}