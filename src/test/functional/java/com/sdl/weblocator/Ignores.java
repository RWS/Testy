package com.sdl.weblocator;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Ignores {

    public static enum Driver {
        ALL,
        CHROME,
        FIREFOX,
        IE
    }

    Driver[] value() default {Driver.ALL};

    String reason() default ("Not implemented in driver yet");
}
