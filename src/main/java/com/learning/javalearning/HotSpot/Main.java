package com.learning.javalearning.HotSpot;

class Test {
    static String version = "1.0";
    String name;
    int id;
    Test(String name, int id) {
        this.name = name;
        this.id = id;
    }
    static void fn() {}
    void fn2(){}
}
 
public class Main {
    static Test t1 = new Test("java", 1);
    private Test t2 = new Test("java", 2);
 
    public void fn() {
        Test t3 = new Test("java", 3);
    }
 
    public static void main(String[] args) {
        new Main().fn();
    }
}