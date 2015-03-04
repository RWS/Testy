package com.extjs.selenium.form;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated pachage "com.extjs.selenium*" is deprecated, please use new package "com.sdl.selenium.extjs3"
 */
public class Radio extends com.sdl.selenium.extjs3.form.Radio {

    public Radio() {
        super();
    }

    public Radio(String value) {
        super(value);
    }

    public Radio(WebLocator container) {
       super(container);
    }

    public Radio(WebLocator container, String name) {
        super(container, name);
    }

    public Radio(String label, WebLocator container) {
        super(label, container);
    }
}