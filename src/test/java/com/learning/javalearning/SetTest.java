package com.learning.javalearning;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * createdAt 2024/4/10
 **/
public class SetTest {
    public static void main(String[] args) {
        String[] a = {"a", "b","c", "d"};
        String[] b = {"a", "f","b", "e"};
        Set<String> aset = new HashSet<>(Arrays.asList(a));
        Set<String> bset = new HashSet<>(Arrays.asList(b));
        System.out.println(aset);
        System.out.println(bset);
        bset.removeAll(aset);
        System.out.println(bset);

        System.out.println(Arrays.toString(bset.toArray(new String[0])));
    }
}
