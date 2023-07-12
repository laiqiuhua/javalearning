package com.learning.javalearning.process;// Test2.java文件

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class Test {

    public static void main(String[] args) throws IOException {
        System.out.println("Default Charset=" + Charset.defaultCharset());
        System.out.println("file.encoding=" + System.getProperty("file.encoding"));
        System.out.println("Default Charset=" + Charset.defaultCharset());


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Input message by parent process:" + br.readLine());
        System.out.println("Local variable parameter（中文）:" + 123);
    }

}
 

