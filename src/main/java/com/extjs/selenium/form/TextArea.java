package com.extjs.selenium.form;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated pachage "com.extjs.selenium*" is deprecated, please use new package "com.sdl.selenium.extjs3"
 */
public class TextArea extends com.sdl.selenium.extjs3.form.TextArea {

    public TextArea() {
        super();
    }

    public TextArea(WebLocator container) {
        super(container);
    }

    public TextArea(WebLocator container, String label) {
       super(container, label);
    }

    public TextArea(String name, WebLocator container) {
       super(name, container);
    }
}