package com.benny.library.kbinding.annotation;

/**
 * Created by benny on 3/1/16.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface ExtractProperty {
    boolean hasPrefix() default true;
    String[] value();
}