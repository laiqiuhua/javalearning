package com.learning.javalearning.sundr;

import io.sundr.model.Kind;
import io.sundr.model.TypeDef;
import io.sundr.model.TypeDefBuilder;

/**
 * createdAt 2023/9/2
 **/
public class GenCodeDemo {

    public static void main(String[] args) {
        TypeDef greeter = new TypeDefBuilder()
                .withKind(Kind.INTERFACE)
                .withName("Greeter")
                .addNewMethod()
                .withName("helloWorld")
                .endMethod()
                .build();

        System.out.println(greeter.render());
    }

}
