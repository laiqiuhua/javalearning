package com.learning.javalearning.stack;

public class StackWalkerCallerExample2 {
    public static void main(String[] args) {
        TheCallerClass sc = new TheCallerClass();
        sc.doSomething();
        System.out.println("util-----"+Util.getCallingClass());

    }

    public static final class TheCallerClass {
        public void doSomething() {
            TheCalleeClass theCalleeClass = new TheCalleeClass();
            theCalleeClass.work();

        }
    }

    public static final class TheCalleeClass {
        public void work() {

            for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                System.out.println(ste.getClassName());
            }
        }
    }
}