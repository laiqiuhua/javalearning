package com.learning.javalearning.spel;

public class RunImpl implements Run {
    @GetVal(spel = "#user.name")
    @Override
    public void run(User user) {
        System.out.println(user.getName() + "正在跑");
    }

}