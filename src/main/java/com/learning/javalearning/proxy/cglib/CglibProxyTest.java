package com.learning.javalearning.proxy.cglib;

/**
 * @Author qhlai
 * @email qhlai@gizwits.com
 */
public class CglibProxyTest {

    public static void main(String[] args) {
        HelloWorldCglib helloWorldCglib = new HelloWorldCglib();

        HelloCglib helloCglib = (HelloCglib) helloWorldCglib.getInstance(new HelloCglib());
        System.out.println(helloCglib.sayHello("qiuhua"));

    }
}
