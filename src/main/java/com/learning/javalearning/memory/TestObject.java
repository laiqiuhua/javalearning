package com.learning.javalearning.memory;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

class TestObject {
    private int a = 1;
    private long b = 2L;
    private String str = "hello";

    public static void main(String[] args) {
        TestObject obj = new TestObject();

        // 查看类布局
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        // 计算对象总大小（包括引用的对象）
        System.out.println("Total size: " + GraphLayout.parseInstance(obj).totalSize() + " bytes");
    }
}
