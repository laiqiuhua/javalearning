package com.learning.javalearning.process;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class TestSystemInAndOut {
 
	public static void main(String[] args) throws IOException {
		try (PrintStream ps = new PrintStream(new FileOutputStream("out.txt"))) {
			PrintStream old = new PrintStream(System.out); // 备份
			System.setOut(ps);
			System.out.println("lalala");
			System.setOut(old); // 还原
		}
		
		try (FileInputStream fis = new FileInputStream("~/IdeaProjects/javalearnning/src/main/java/com/learnning/javalearnning/process/Test.java")) {
			System.setIn(fis);
			Scanner sc = new Scanner(System.in);
			sc.useDelimiter("\n");
			while (sc.hasNext()) {
				System.out.println(sc.next());
			}
		}
	}
}