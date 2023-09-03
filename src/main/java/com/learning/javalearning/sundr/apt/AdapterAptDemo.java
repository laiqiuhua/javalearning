package com.learning.javalearning.sundr.apt;

import io.sundr.adapter.api.AdapterContext;
import io.sundr.adapter.api.Adapters;
import io.sundr.adapter.apt.AptContext;
import io.sundr.model.TypeDef;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.Set;

/**
 * createdAt 2023/9/2
 **/
public class AdapterAptDemo extends AbstractProcessor {
    public static void main(String[] args) {
        TypeDef runnable = Adapters.adaptType(Runnable.class, AdapterContext.getContext());
        System.out.println(runnable.render());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Elements elements = processingEnv.getElementUtils();
        Types types = processingEnv.getTypeUtils();

        TypeDef def = Adapters.adaptType(Runnable.class, AptContext.create(elements, types));
        return false;
    }
}
