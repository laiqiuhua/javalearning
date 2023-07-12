package com.learning.javalearning.process;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 远程执行，设定文件编码 Dfile.encoding=UTF-8, 否则会乱码
 */
public class Test_Exec_In {
    public static void main(String[] args) throws IOException {
        Runtime run = Runtime.getRuntime();
        Process p = run.exec("ps -ef");
//        Process p = run.exec("java -Dfile.encoding=UTF-8 -classpath /Users/laiqiuhua/IdeaProjects/javalearnning/target/classes/ com.learnning.javalearnning.process.Test");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
        bw.write("I'm your parent process(中文)");
        bw.flush();
        bw.close(); // 必须得关闭流，否则无法向子进程中输入信息

        // 进程正常返回信息
        BufferedInputStream in = new BufferedInputStream(p.getInputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String s;
        while ((s = br.readLine()) != null) {
            System.out.println(s);
        }


        // 进程错误返回信息
        BufferedInputStream errorin = new BufferedInputStream(p.getErrorStream());
        BufferedReader errorbr = new BufferedReader(new InputStreamReader(errorin));
        String s1;
        while ((s1 = errorbr.readLine()) != null) {
            System.out.println(s1);
        }
    }
}