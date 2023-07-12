package com.learning.javalearning.instrument;

import java.lang.instrument.Instrumentation;

/**
 * @Author qhlai
 * @email qhlai@gizwits.com
 */
public class MyAgent {

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println(agentArgs);
        Class[] classes = inst.getAllLoadedClasses();
        for (Class claz: classes) {
            System.out.println("classname:"+claz.getName()+" type:"+claz.getTypeName());
        }

    }

}
