package com.learning.javalearning.base;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author qhlai
 * @email qhlai@gizwits.com
 */
public class BaseJavaType {
    public static void main(String[] args) {
        System.out.println("Integer: " + ObjectSizeCalculator.getObjectSize(12l));
        System.out.println("Integer: " + ObjectSizeCalculator.getObjectSize(Integer.valueOf(122)));
        System.out.println("Long: " + ObjectSizeCalculator.getObjectSize(Long.valueOf(122L)));
        System.out.println("Double: " + ObjectSizeCalculator.getObjectSize(Double.valueOf(122.22)));
        System.out.println("Float: " + ObjectSizeCalculator.getObjectSize(Float.valueOf(122.22f)));
        System.out.println("Boolean: " + ObjectSizeCalculator.getObjectSize(Boolean.valueOf(false)));
        System.out.println("Character: " + ObjectSizeCalculator.getObjectSize(Character.valueOf('a')));
        System.out.println("Short: " + ObjectSizeCalculator.getObjectSize(Short.valueOf("1")));
        System.out.println("Byte: " + ObjectSizeCalculator.getObjectSize(Byte.valueOf("1")));
        System.out.println("Date: " + ObjectSizeCalculator.getObjectSize(new Date()));
        System.out.println("Timestamp: " + ObjectSizeCalculator.getObjectSize(new Timestamp(System.currentTimeMillis())));

        Map<String, Object> map = new HashMap<>();
        map.put("11", 11);
        map.put("22", "22");
        map.put("33", 33L);
        map.put("44", 44.44);
        System.out.println("Map: " + ObjectSizeCalculator.getObjectSize(map));
    }
}
