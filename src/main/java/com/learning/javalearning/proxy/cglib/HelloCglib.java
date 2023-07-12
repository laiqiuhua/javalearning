package com.learning.javalearning.proxy.cglib;

/**
 * @Author qhlai
 * @email qhlai@gizwits.com
 */
public class HelloCglib {

    public String sayHello(String name) {
        System.out.println("I'm name:"+ name);
        return name;
    }
}
