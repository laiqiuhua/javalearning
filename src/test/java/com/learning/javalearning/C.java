package com.learning.javalearning;

/**
 * @Author qhlai
 * @email qhlai@gizwits.com
 */
public class C implements A, B {

    public void printSuper() {
        System.out.println(super.getClass());
    }
    public static void main(String[] args) {
        new C().sayHello();
        A.staticTest();
        B.staticTest();
       new C().printSuper();
    }
}


interface A {
    default void sayHello() {
        System.out.println("Hello,i'm hello A");
    }

    static void staticTest() {
        System.out.println("Hello, i'm a static method A");
    }

}

interface B extends A {
    default void sayHello() {
        System.out.println("Hello,i'm hello B");
    }

    static void staticTest() {
        System.out.println("Hello, i'm a static method B");
    }
}
