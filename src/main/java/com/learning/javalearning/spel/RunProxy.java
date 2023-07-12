package com.learning.javalearning.spel;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class RunProxy implements InvocationHandler {

    private Object target;

    public RunProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 由于demo的动态代理使用接口无法获取到形参名称，所以转换为实现类的方法对象
        method = target.getClass().getMethod(method.getName(), method.getParameterTypes());
        Object res = method.invoke(target, args);
        DefaultParameterNameDiscoverer defaultParameterNameDiscoverer = new DefaultParameterNameDiscoverer();
        // 这里参考的org.springframework.context.expression.MethodBasedEvaluationContext.lazyLoadArguments
        String[] parameterNames = defaultParameterNameDiscoverer.getParameterNames(method);

        GetVal annotation = method.getAnnotation(GetVal.class);
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(annotation.spel());
        StandardEvaluationContext ctx = new StandardEvaluationContext();
        // 填充表达式上下文环境
        for(int i=0;i<parameterNames.length;i++){
            ctx.setVariable(parameterNames[i],args[i]);
        }
        String value = expression.getValue(ctx, String.class);
        // 打印日志
        System.out.println(value+"执行了"+method.getName());
        return res;
    }
}
