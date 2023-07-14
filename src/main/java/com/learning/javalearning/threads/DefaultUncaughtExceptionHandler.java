package com.learning.javalearning.threads;

/**
 *
 * 处理多线程异常方式
 * 1. UncaughtExceptionHandler 统一处理
 * 2. 通过暂存起来，后续处理
 * 当单线程的程序发生一个未捕获的异常时我们可以采用try….catch进行异常的捕获，但是在多线程环境中，线程抛出的异常是不能用try….catch捕获的，这样就有可能导致一些问题的出现，比如异常的时候无法回收一些系统资源，或者没有关闭当前的连接等等。
 */
public class DefaultUncaughtExceptionHandler {

    public static void main(String[] args) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(new MyHandler());

        throw new Exception("A test exception");
    }

    private static final class MyHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println(t.getThreadGroup());
            System.out.println(t.getContextClassLoader());

            System.out.println("exception caught:" + e);
        }
    }
}