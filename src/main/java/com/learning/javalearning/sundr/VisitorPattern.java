package com.learning.javalearning.sundr;

import io.sundr.adapter.api.AdapterContext;
import io.sundr.adapter.api.Adapters;
import io.sundr.model.PropertyBuilder;
import io.sundr.model.TypeDef;
import io.sundr.model.TypeDefBuilder;

import java.util.Collections;

/**
 * createdAt 2023/9/2
 **/
public class VisitorPattern {
    public static void main(String[] args) {
        TypeDef person = Adapters.adaptType(Person.class, AdapterContext.getContext());
        TypeDef dto = new TypeDefBuilder(person)
                .withName(person.getName() + "DTO")
                .withMethods(Collections.emptyList())
                .withConstructors(Collections.emptyList())
                .withExtendsList(Collections.emptyList())
                .accept(PropertyBuilder.class, property -> {
                    property.withNewModifiers().withPublic().endModifiers();
                }).build();
        System.out.println(dto.render());
    }
}
