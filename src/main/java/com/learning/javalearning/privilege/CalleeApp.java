package com.learning.javalearning.privilege;

import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

public class CalleeApp {

    @CallerSensitive
    public void call() {

        Class<?> clazz = Reflection.getCallerClass();
        System.out.println("Hello " + clazz);
    }
}