package com.learning.javalearning.jpda.hsdb;

public class Main {
    public static void main(String[] args) {
        System.out.println("start");
        Test test = new Test();
        test.fn();
        System.out.println("end");
    }
}