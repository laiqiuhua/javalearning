package com.learning.javalearning.threads;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @Author qhlai
 * @email qhlai@gizwits.com
 */
public class ThreadGroupTest {

    public static void main(String[] args) {
        ThreadGroup threadGroup = new ThreadGroup("thread-group");
        ThreadGroup threadGroup1 = new ThreadGroup("thread-group1");
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        Thread t1 = threadFactory.newThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("--------------t1--------");
                System.out.println(Thread.currentThread().getName()+"---"+ Thread.currentThread().getThreadGroup()+ "state"+Thread.currentThread().getState());
                System.out.println(Thread.currentThread().getName()+"---"+ Thread.currentThread().getThreadGroup().getParent());
                System.out.println(Thread.currentThread().getThreadGroup().activeCount()+"--activegroup:"+ Thread.currentThread().getThreadGroup().activeGroupCount());
                System.out.println(Thread.currentThread().getThreadGroup().getParent().activeCount()+"--activegroup:"+ Thread.currentThread().getThreadGroup().getParent().activeGroupCount());
                System.out.println("--------------t1--------");
                System.out.println("-------------current--------");
                Thread.currentThread().getThreadGroup().list();
                System.out.println("--------------parent--------");
                Thread.currentThread().getThreadGroup().getParent().list();

            }
        });

        Thread t = new Thread(threadGroup, new Runnable() {
            @Override
            public void run() {
                System.out.println("--------------t--------");
                System.out.println(Thread.currentThread().getName()+"---"+ Thread.currentThread().getThreadGroup()+ "state"+Thread.currentThread().getState());
                System.out.println(Thread.currentThread().getName()+"---"+ Thread.currentThread().getThreadGroup().getParent());
                System.out.println(Thread.currentThread().getThreadGroup().activeCount()+"--activegroup:"+ Thread.currentThread().getThreadGroup().activeGroupCount());
                System.out.println(Thread.currentThread().getThreadGroup().getParent().activeCount()+"--activegroup:"+ Thread.currentThread().getThreadGroup().getParent().activeGroupCount());
                System.out.println("--------------t--------");
                System.out.println("-------------current--------");
                Thread.currentThread().getThreadGroup().list();
                System.out.println("--------------parent--------");
                Thread.currentThread().getThreadGroup().getParent().list();
                System.out.println("--------------parent parent--------");
                Thread.currentThread().getThreadGroup().getParent().getParent().list();
            }
        }, "test-thread");
        Thread t2 = new Thread(threadGroup1, new Runnable() {
            @Override
            public void run() {
                System.out.println("--------------t2--------");
                System.out.println(Thread.currentThread().getName()+"---"+ Thread.currentThread().getThreadGroup()+ "state"+Thread.currentThread().getState());
                System.out.println(Thread.currentThread().getName()+"---"+ Thread.currentThread().getThreadGroup().getParent());
                System.out.println(Thread.currentThread().getThreadGroup().activeCount()+"--activegroup:"+ Thread.currentThread().getThreadGroup().activeGroupCount());
                System.out.println(Thread.currentThread().getThreadGroup().getParent().activeCount()+"--activegroup:"+ Thread.currentThread().getThreadGroup().getParent().activeGroupCount());
                System.out.println("--------------t2--------");

                System.out.println("-------------current--------");
                Thread.currentThread().getThreadGroup().list();
                System.out.println("--------------parent--------");
                Thread.currentThread().getThreadGroup().getParent().list();
                System.out.println("--------------parent parent--------");
                Thread.currentThread().getThreadGroup().getParent().getParent().list();
            }
        }, "test-thread1");

        t.start();
//        t1.start();
//        t2.start();


        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement s: stackTraceElements) {
            System.out.println("class:"+s.getClassName()+"-method:"+s.getMethodName()+"-linenumber:"+s.getLineNumber()+"-filename:"+s.getFileName());
        }

        StackTraceElement[] stackTraceElements1 =  new RuntimeException().getStackTrace();
        for (StackTraceElement s: stackTraceElements1) {
            System.out.println("class:"+s.getClassName()+"-method:"+s.getMethodName()+"-linenumber:"+s.getLineNumber()+"-filename:"+s.getFileName());
        }

    }
}
