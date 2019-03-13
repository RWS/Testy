package com.sdl.selenium.extjs6.form;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
//@Retention(RetentionPolicy.CLASS)
@Target({
        ElementType.FIELD,
        ElementType.TYPE_USE,
        ElementType.METHOD,
        ElementType.CONSTRUCTOR,
        ElementType.LOCAL_VARIABLE,
        ElementType.TYPE,
        ElementType.ANNOTATION_TYPE,
        ElementType.PACKAGE,
        ElementType.TYPE_PARAMETER,
        ElementType.PARAMETER
})
public @interface Version {
    String version() default "";
}
