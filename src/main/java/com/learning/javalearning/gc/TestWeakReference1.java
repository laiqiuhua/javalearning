package com.learning.javalearning.gc;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试java引用回收机制
 */
public class TestWeakReference1 {

    public static void main(String[] args) {
        weakObject();
        weakList();

    }

    public static void weakList() {
        System.out.println("--------weakList---------");
        Object o = new Object();
        List<Object> list = new ArrayList<>();
        WeakReference<List<Object>> wrL = new WeakReference<>(list);
        list.add(o);
        System.out.println(o);
        list.remove(o);
        o = null;
        System.gc();
        System.out.println(wrL.get());//null
        System.out.println(o);
    }


    public static void weakObject() {
        System.out.println("--------weakList---------");
        Object o = new Object();
        WeakReference<Object> wr = new WeakReference<>(o);
        System.out.println(o);
        o = null;
        System.gc();
        System.out.println(wr.get());//null

    }

}