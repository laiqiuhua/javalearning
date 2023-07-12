package com.learning.javalearning.jpda.hsdb;

public class Test {
    static Test2 t1 = new Test2();
    Test2 t2 = new Test2();
    public void fn() {
        Test2 t3 = new Test2();
        System.out.println("-----fnfnfn-----");
    }
}
class Test2 {
}