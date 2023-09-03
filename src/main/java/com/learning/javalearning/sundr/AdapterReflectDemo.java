package com.learning.javalearning.sundr;

import io.sundr.adapter.api.AdapterContext;
import io.sundr.adapter.api.Adapters;
import io.sundr.model.TypeDef;
import io.sundr.model.TypeDefBuilder;

/**
 * createdAt 2023/9/2
 **/
public class AdapterReflectDemo {

    public static void main(String[] args) {
        TypeDef def = Adapters.adaptType(GenCodeDemo.class, AdapterContext.getContext());
        System.out.println(def.render());

        System.out.println("-------- after manipulation ----------");
        TypeDef forrest = new TypeDefBuilder(def).withName("Forrest").build();
        System.out.println(forrest.render());
    }
}
