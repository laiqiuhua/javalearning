package com.learning.javalearning;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.LoggingEvent;
import org.junit.Test;

/**
 * @Author qhlai
 * @email qhlai@gizwits.com
 */
public class AppTests {

    public static void main(String[] args) {
        StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            System.out.println(stackTraceElement.getClassName() + "--" + stackTraceElement.getMethodName() + "--" + stackTraceElement.getLineNumber() + "--" + stackTraceElement.getFileName());
            if ("main".equals(stackTraceElement.getMethodName())) {
                System.out.println(stackTraceElement.getClassName());
//                    Class.forName(stackTraceElement.getClassName());
            }
        }
    }

    @Test
    public void testDeduceMainApplicationClass() {
        try {
            StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace) {
                System.out.println(stackTraceElement.getClassName() + "--" + stackTraceElement.getMethodName() + "--" + stackTraceElement.getLineNumber() + "--" + stackTraceElement.getFileName());
                if ("main".equals(stackTraceElement.getMethodName())) {
                    System.out.println(stackTraceElement.getClassName());
                    Class.forName(stackTraceElement.getClassName());
                }
            }
        } catch (ClassNotFoundException ex) {
            // Swallow and continue
        }
    }

    public class CSecurityManager extends SecurityManager {
        protected Class<?>[] getClassContext() {
            return super.getClassContext();
        }
    }

    @Test
    public void testStack() {
        CSecurityManager securityManager = new CSecurityManager();
//	    Util.ClassContextSecurityManager securityManager = getSecurityManager();
        if (securityManager == null)
            return;
        Class<?>[] trace = securityManager.getClassContext();
        String thisClassName = AppTests.class.getName();

        // Advance until Util is found
        int i;
        for (i = 0; i < trace.length; i++) {
            System.out.println(trace[i].getName());
            if (thisClassName.equals(trace[i].getName()))
                break;
        }
        System.out.println(i + "  " + trace[i + 2]);
    }

    @Test
    public void testLog() {
//        String localFQCN = AppTests.class.getName();
        String localFQCN = "java.lang.reflect.Method" ;
        Level level = Level.ALL;
        String msg = "test msg";
        Throwable t = null;
        Object[] params = null;


        LoggingEvent le = new LoggingEvent(localFQCN, new LoggerContext().getLogger("test"), level, msg, t, params);
        System.out.println(le.getLoggerName());
//        le.setCallerData(new Throwable().getStackTrace());
        StackTraceElement[] stackTrace = le.getCallerData();

        for (StackTraceElement stackTraceElement : stackTrace) {
            System.out.println(stackTraceElement.getClassName() + "--" + stackTraceElement.getMethodName() + "--" + stackTraceElement.getLineNumber() + "--" + stackTraceElement.getFileName());
        }

        System.out.println("first separation:-------------------------------------");

        StackTraceElement[] st = CallerData.extract(new Throwable(), localFQCN, 1,  null);

        for (StackTraceElement stackTraceElement : st) {
            System.out.println(stackTraceElement.getClassName() + "-st-" + stackTraceElement.getMethodName() + "--" + stackTraceElement.getLineNumber() + "--" + stackTraceElement.getFileName());
        }


        System.out.println("second separation----------------------");
        StackTraceElement[] st1 = new Throwable().getStackTrace();
        for (StackTraceElement stackTraceElement : st1) {
            System.out.println(stackTraceElement.getClassName() + "-st-" + stackTraceElement.getMethodName() + "--" + stackTraceElement.getLineNumber() + "--" + stackTraceElement.getFileName());
        }
    }



}
