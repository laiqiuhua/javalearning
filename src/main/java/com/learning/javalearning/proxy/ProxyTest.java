package com.learning.javalearning.proxy;

import java.lang.reflect.Proxy;

/**
 * @Author qhlai
 * @email qhlai@gizwits.com
 */
public class ProxyTest {

    public static void main(String[] args) {
        HelloWorld helloWorld = new HelloworldImpl();
        HelloWorld helloWorld1 = (HelloWorld) Proxy.newProxyInstance(helloWorld.getClass().getClassLoader(), helloWorld.getClass().getInterfaces(), new MyProxy(helloWorld));
        System.out.println(helloWorld1.sayHello("qiuhua"));
    }
}
