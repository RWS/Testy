package com.extjs.selenium.form;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated pachage "com.extjs.selenium*" is deprecated, please use new package "com.sdl.selenium.extjs3"
 */
public class TextField extends com.sdl.selenium.extjs3.form.TextField {

    public TextField() {
       super();
    }

    public TextField(String cls) {
        super(cls);
    }

    public TextField(WebLocator container) {
        super(container);
    }

    public TextField(WebLocator container, String label) {
       super(container, label);
    }

    public TextField(String name, WebLocator container) {
        super(name, container);
    }
}