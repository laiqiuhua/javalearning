//package com.learnning.javalearnning.stack;
//
//public class StackWalkerCallerExample3 {
//    public static void main(String[] args) {
//        TheCallerClass sc = new TheCallerClass();
//        sc.doSomethingReflectively();
//    }
//
//    public static class TheCallerClass {
//        public void doSomethingReflectively() {
//            try {
//                TheCalleeClass theCallerClass = TheCalleeClass.class
//                        .getConstructor().newInstance();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static class TheCalleeClass {
//        public TheCalleeClass() {
//            System.out.println("-- using StackWalker --");
//            StackWalker instance = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
//            Class<?> callerClass = instance.getCallerClass();
//            System.out.println(callerClass);
//
//            System.out.println("-- using stack trace --");
//            for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
//                System.out.println(ste.getClassName());
//            }
//        }
//    }
//}