package com.learning.javalearning.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author qhlai
 * @email qhlai@gizwits.com
 */
public class MyProxy implements InvocationHandler {
    private HelloWorld helloWorld;

    public MyProxy(HelloWorld helloWorld) {
        this.helloWorld = helloWorld;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(proxy.getClass());
        System.out.println("---------------invoke before----------");
        Object result = method.invoke(helloWorld, args);
        System.out.println("---------------invoke after----------");
        return result;
    }


}
