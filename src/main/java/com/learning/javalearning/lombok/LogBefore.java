package com.learning.javalearning.lombok;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * Date: 2018/5/26
 * @author xuzhiyi
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface LogBefore {
    String level() default "info";
}
