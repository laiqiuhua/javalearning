package com.learning.javalearning.proxy.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Author qhlai
 * @email qhlai@gizwits.com
 */
public class HelloWorldCglib implements MethodInterceptor {
    private Enhancer enhancer = new Enhancer();
    private Object target;

    public Object getInstance(Object target) {
        this.target = target;
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println(o.getClass());
        System.out.println("supername:"+methodProxy.getSuperName());
        System.out.println("name:"+methodProxy.getSignature().getName());
        System.out.println("descriptor:"+methodProxy.getSignature().getDescriptor());
        System.out.println("arguments:"+Arrays.toString(methodProxy.getSignature().getArgumentTypes()));
        System.out.println("result:"+ methodProxy.getSignature().getReturnType());
        System.out.println("-------cglib before---------");
        Object result = method.invoke(target, objects);
        System.out.println("-------cglib before---------");
        return result;
    }


}
