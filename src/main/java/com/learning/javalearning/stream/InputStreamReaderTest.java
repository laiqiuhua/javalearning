package com.learning.javalearning.stream;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputStreamReaderTest {

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream("src/test.tmp"), "UTF8"), 1024)) {
            System.out.println(reader.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //示例，声明自己的两个资源类，实现AutoCloseable接口。
        try (MyResource myResource = new MyResource();
                MyResource2 myResource2 = new MyResource2()) {
            myResource.readResource();
            myResource2.readResource();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
class MyResource implements AutoCloseable {

    @Override
    public void close() throws Exception {
        System.out.println("close resource");
    }

    public void readResource() {
        System.out.println("read resource");
    }

}

class MyResource2 implements Closeable {

    @Override
    public void close() throws IOException {
        System.out.println("close resource2");
    }

    public void readResource() {
        System.out.println("read resource2");
    }

}