package com.learning.javalearning.reflect;

import org.springframework.util.TypeUtils;

import java.util.Arrays;

/**
 * @Buthor qhlai
 * @email qhlai@gizwits.com
 */
public class TestBClass {
    public static void main(String[] args) {
        System.out.println(B.class.asSubclass(IB.class));
        System.out.println(IB.class.asSubclass(IA.class));
        System.out.println(B.class.asSubclass(A.class));
        System.out.println(IA.class.isAssignableFrom(IC.class));
        System.out.println(TestEnum.class.asSubclass(IA.class));
        System.out.println(TypeUtils.isAssignable(IA.class, B.class));
        System.out.println(new A<String, Object, TestEnum>().getClass().getTypeParameters()[0]);
        printClass();
        System.out.println("=========================");
        printInstance();

    }

    public static void printClass() {
        System.out.println(B.class.getClassLoader());
        System.out.println(B.class.getClass());
        System.out.println(Arrays.toString(B.class.getInterfaces()));
        System.out.println(B.class.getSuperclass());
        System.out.println(B.class.getGenericSuperclass());
        System.out.println(Arrays.toString(B.class.getGenericInterfaces()));
        System.out.println(B.class.getDeclaringClass());
        System.out.println(B.class.getModifiers());
        System.out.println(B.class.getName());
        System.out.println(B.class.getComponentType());
        System.out.println(B.class.getConstructors());
        System.out.println(B.class.getEnclosingMethod());
        System.out.println(B.class.getEnumConstants());
        System.out.println(B.class.getSigners());
        System.out.println(B.class.getSimpleName());
        System.out.println(B.class.getSuperclass());
        System.out.println(B.class.getTypeName());
        System.out.println(Arrays.toString(B.class.getTypeParameters()));
        System.out.println(B.class.getAnnotatedInterfaces()[0].getType());
        System.out.println(Arrays.toString(B.class.getAnnotations()));
        System.out.println(B.class.getAnnotatedSuperclass());
        System.out.println(Arrays.toString(B.class.getDeclaredAnnotations()));
        System.out.println(B.class.getAnnotation(TestA.class));
        System.out.println(B.class.getDeclaredAnnotation(TestA.class));

        System.out.println(((Lamdatest) str -> "1111"));

        System.out.println(new B().getClass().getEnclosingClass());

        System.out.println("anno:" + B.class.isAnnotation());
        System.out.println("isBnony:" + ((Lamdatest) str -> "").getClass().isAnonymousClass());
        System.out.println("isBrray:" + new B[5].getClass().isArray());
        System.out.println("isEnum:" + B.class.isEnum());
        System.out.println("isInterface:" + B.class.isInterface());
        System.out.println("isLocalClass:" + Thread.class.isLocalClass());
        System.out.println("isMember:" + B.class.isMemberClass());
        System.out.println("isPrimitive:" + B.class.isPrimitive());
        System.out.println("isSynthethic:" + B.class.isSynthetic());
    }

    public static void printInstance() {
        B a = new B();
        System.out.println(a.getClass().getClassLoader());
        System.out.println(a.getClass().getClass());
        System.out.println(Arrays.toString(B.class.getInterfaces()));
        System.out.println(a.getClass().getGenericSuperclass());
        System.out.println(a.getClass().getDeclaringClass());
        System.out.println(a.getClass().getModifiers());
        System.out.println(a.getClass().getName());
        System.out.println(new B[5].getClass().getComponentType());
        System.out.println(a.getClass().getConstructors());
        System.out.println(a.getClass().getEnclosingMethod());
        System.out.println(a.getClass().getEnumConstants());
        System.out.println(a.getClass().getSigners());
        System.out.println(a.getClass().getSimpleName());
        System.out.println(a.getClass().getSuperclass());
        System.out.println(a.getClass().getTypeName());
        System.out.println(Arrays.toString(a.getClass().getTypeParameters()));


        System.out.println("anno:" + a.getClass().isAnnotation());
        System.out.println("isBnony:" + a.getClass().isAnonymousClass());
        System.out.println("isBrray:" + a.getClass().isArray());
        System.out.println("isEnum:" + a.getClass().isEnum());
        System.out.println("isInterface:" + a.getClass().isInterface());
        System.out.println("isLocalClass:" + a.getClass().isLocalClass());
        System.out.println("isMember:" + a.getClass().isMemberClass());
        System.out.println("isPrimitive:" + a.getClass().isPrimitive());
        System.out.println("isSynthethic:" + a.getClass().isSynthetic());
    }
}
