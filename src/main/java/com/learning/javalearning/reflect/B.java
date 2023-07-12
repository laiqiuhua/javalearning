package com.learning.javalearning.reflect;

/**
 * @Author qhlai
 * @email qhlai@gizwits.com
 */
public class B extends A<String, Object, TestEnum> implements IB<String>, IC {
    private String f4;
    private double f5;

    public static class TestInner {

    }

    static TestInner testInner1 = new TestInner(){};
}
