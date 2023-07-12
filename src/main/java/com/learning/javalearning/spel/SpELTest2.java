package com.learning.javalearning.spel;

import java.lang.reflect.Proxy;

public class SpELTest2 {

    public static void main(String[] args) {
        User user = new User("三侃");
        RunImpl runImpl = new RunImpl();
        RunProxy proxy = new RunProxy(runImpl);
        Run run = (Run) Proxy.newProxyInstance(runImpl.getClass().getClassLoader(), runImpl.getClass().getInterfaces(), proxy);
        run.run(user);
    }

}