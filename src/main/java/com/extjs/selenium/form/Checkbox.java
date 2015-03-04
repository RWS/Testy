package com.extjs.selenium.form;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated pachage "com.extjs.selenium*" is deprecated, please use new package "com.sdl.selenium.extjs3"
 */
public class Checkbox extends com.sdl.selenium.extjs3.form.Checkbox {

    public Checkbox() {
        super();
    }

    public Checkbox(WebLocator container) {
        super(container);
    }

    public Checkbox(WebLocator container, String name) {
        super(container, name);
    }
}