package com.learning.javalearning.sundr;

import io.sundr.adapter.api.AdapterContext;
import io.sundr.adapter.api.Adapters;
import io.sundr.model.TypeDef;

import java.io.File;

/**
 * createdAt 2023/9/2
 **/
public class AdapterSourceDemoJava {

    public static void main(String[] args) {
        String filepath = "/Users/laiqiuhua/workspaces/IdeaProjects/javalearnning/src/main/java/com/learning/javalearning/sundr/GenCodeDemo.java";
        TypeDef genCodeDemo = Adapters.adaptType(new File(filepath), AdapterContext.getContext());
        System.out.println(genCodeDemo.render());
    }
}
