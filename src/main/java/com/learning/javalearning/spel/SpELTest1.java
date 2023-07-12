package com.learning.javalearning.spel;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpELTest1 {

    public static void main(String[] args) {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("getInput()");
        StandardEvaluationContext ctx = new StandardEvaluationContext();
        User user = new User("三侃");
        // 设置需要执行方法的类
        ctx.setRootObject(user);
        String value = expression.getValue(ctx, String.class);
        System.out.println(value);
    }

    public static class User{
        private String name;
        public User(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public String getInput(){
            return "我叫："+this.name;
        }
    }

}