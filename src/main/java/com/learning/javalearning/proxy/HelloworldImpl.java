package com.learning.javalearning.proxy;

/**
 * @Author qhlai
 * @email qhlai@gizwits.com
 */
public class HelloworldImpl implements HelloWorld {
    @Override
    public String sayHello(String name) {
        System.out.println("I'm name:"+name);
        return name;
    }
}
