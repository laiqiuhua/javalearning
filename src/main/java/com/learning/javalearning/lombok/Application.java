package com.learning.javalearning.lombok;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class Application {

    @LogBefore
    static void m1(Map<String, String> req) {
        System.out.println("m1 running");
    }
}
