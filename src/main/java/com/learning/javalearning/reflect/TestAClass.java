package com.learning.javalearning.reflect;

import java.util.Arrays;

/**
 * @Author qhlai
 * @email qhlai@gizwits.com
 */
public class TestAClass {
    public static void main(String[] args) {
        printClass();
        System.out.println("=============");
        printInstance();
       
    }


    public static void printClass() {
        System.out.println(A.class.getClassLoader());
        System.out.println(A.class.getClass());
        System.out.println(Arrays.toString(A.class.getInterfaces()));
        System.out.println(A.class.getGenericSuperclass());
        System.out.println(A.class.getDeclaringClass());
        System.out.println(A.class.getModifiers());
        System.out.println(A.class.getName());
        System.out.println(A.class.getComponentType());
        System.out.println(A.class.getConstructors());
        System.out.println(A.class.getEnclosingMethod());
        System.out.println(A.class.getEnumConstants());
        System.out.println(A.class.getSigners());
        System.out.println(A.class.getSimpleName());
        System.out.println(A.class.getSuperclass());
        System.out.println(A.class.getTypeName());
        System.out.println(Arrays.toString(A.class.getTypeParameters()));
        System.out.println(A.class.getAnnotatedInterfaces()[0].getType());
        System.out.println(Arrays.toString(A.class.getAnnotations()));
        System.out.println(A.class.getAnnotatedSuperclass());
        System.out.println(Arrays.toString(A.class.getDeclaredAnnotations()));
        System.out.println(A.class.getAnnotation(TestA.class));
        System.out.println(A.class.getDeclaredAnnotation(TestA.class));



        System.out.println("anno:"+A.class.isAnnotation());
        System.out.println("isAnony:"+A.class.isAnonymousClass());
        System.out.println("isArray:"+A.class.isArray());
        System.out.println("isEnum:"+A.class.isEnum());
        System.out.println("isInterface:"+A.class.isInterface());
        System.out.println("isLocalClass:"+A.class.isLocalClass());
        System.out.println("isMember:"+A.class.isMemberClass());
        System.out.println("isPrimitive:"+A.class.isPrimitive());
        System.out.println("isSynthethic:"+A.class.isSynthetic());
    }

    public static void printInstance() {
        A a = new A();
        System.out.println(a.getClass().getClassLoader());
        System.out.println(a.getClass().getClass());
        System.out.println(Arrays.toString(A.class.getInterfaces()));
        System.out.println(a.getClass().getGenericSuperclass());
        System.out.println(a.getClass().getDeclaringClass());
        System.out.println(a.getClass().getModifiers());
        System.out.println(a.getClass().getName());
        System.out.println(a.getClass().getComponentType());
        System.out.println(a.getClass().getConstructors());
        System.out.println(a.getClass().getEnclosingMethod());
        System.out.println(a.getClass().getEnumConstants());
        System.out.println(a.getClass().getSigners());
        System.out.println(a.getClass().getSimpleName());
        System.out.println(a.getClass().getSuperclass());
        System.out.println(a.getClass().getTypeName());
        System.out.println(Arrays.toString(a.getClass().getTypeParameters()));


        System.out.println("anno:"+a.getClass().isAnnotation());
        System.out.println("isAnony:"+a.getClass().isAnonymousClass());
        System.out.println("isArray:"+a.getClass().isArray());
        System.out.println("isEnum:"+a.getClass().isEnum());
        System.out.println("isInterface:"+a.getClass().isInterface());
        System.out.println("isLocalClass:"+a.getClass().isLocalClass());
        System.out.println("isMember:"+a.getClass().isMemberClass());
        System.out.println("isPrimitive:"+a.getClass().isPrimitive());
        System.out.println("isSynthethic:"+a.getClass().isSynthetic());
    }

}
