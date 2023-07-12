package com.learning.javalearning.spel;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpELTest {

    public static void main(String[] args) {
        // 表达式解析器
        ExpressionParser parser = new SpelExpressionParser();
        // 解析出一个表达式
        Expression expression = parser.parseExpression("#user.name");
        // 开始准备表达式运行环境
        EvaluationContext ctx = new StandardEvaluationContext();
        ctx.setVariable("user", new User("三侃"));
        String value = expression.getValue(ctx, String.class);
        Object value1 = expression.getValue(ctx);
        System.out.println(value);
        System.out.println(value1);
    }

    public static class User{
        private String name;
        public User(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
    }

}