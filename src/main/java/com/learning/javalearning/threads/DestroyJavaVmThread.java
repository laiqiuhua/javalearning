package com.learning.javalearning.threads;

/**
 * 这是因为大多数应用程序都在线程中运行。
 * 所有POJO应用程序都从调用main方法开始。在最简单的情况下，这个方法将完成所有的工作，创建对象，调用方法等等。一旦主进程完成，JVM将被告知使用DestroyJavaVM线程关闭，该线程将等待所有非守护进程线程完成，然后再执行它的工作。这是为了确保您创建的任何非守护进程线程在JVM被销毁之前运行到完成。
 * 然而，一个带有GUI的应用程序通常作为许多线程运行。一种用于监视系统事件，如键盘或鼠标事件。一个用于维护窗口和显示等。这类应用程序的主方法可能只是启动所有需要的线程并退出。它仍然创建DestroyJavaVM线程，但现在所做的就是等待所有创建的线程完成后再销毁VM。
 * 因此，任何创建线程并只依赖其功能的应用程序都会有一个DestroyJavaVM线程等待它完成。因为它所做的就是连接所有其他正在运行的线程，所以它不消耗任何资源。
 *
 * @Author qhlai
 * @email qhlai@gizwits.com
 */
public class DestroyJavaVmThread {

    public static void main(String[] args) {
        ThreadGroup threadGroup = new ThreadGroup("sub-tread");
        Thread thread = new Thread(threadGroup, new Runnable() {
            @Override
            public void run() {
                ThreadGroup threadGroup1 = new ThreadGroup("sub-tread1");
                Thread thread1 = new Thread(threadGroup1, new Runnable() {

                    @Override
                    public void run() {
                        ThreadGroup currGourp = Thread.currentThread().getThreadGroup();
                        while (currGourp != null) {
                            System.out.println("******************************************");
                            System.out.println("sub sub group name:"+currGourp.getName());
                            currGourp.list();
                            currGourp = currGourp.getParent();
                        }

                    }
                });

                System.out.println("******************************************");
                ThreadGroup currGourp = Thread.currentThread().getThreadGroup();
                while (currGourp != null) {
                    System.out.println("sub group name:"+currGourp.getName());
                    currGourp.list();
                    currGourp = currGourp.getParent();
                }

                thread1.start();


            }
        });

        System.out.println("******************************************");
        ThreadGroup currGourp = Thread.currentThread().getThreadGroup();
        while (currGourp != null) {
            System.out.println("main group name:"+currGourp.getName());
            currGourp.list();
            currGourp = currGourp.getParent();
        }


        /**
         * 销毁此线程组及其所有子组。此线程组必须为空，表示此线程组中的所有线程都已停止。
         * 首先，调用这个线程组的checkAccess方法，不带参数;这可能会导致安全异常。
         */
        thread.start();
//        threadGroup.destroy();
//        thread.interrupt();



    }



}
