package com.extjs.selenium.form;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated pachage "com.extjs.selenium*" is deprecated, please use new package "com.sdl.selenium.extjs3"
 */
public class Label extends com.sdl.selenium.extjs3.form.Label {

    public Label() {
        super();
    }

    public Label(String text) {
       super(text);
    }

    public Label(WebLocator container) {
        super(container);
    }

    public Label(WebLocator container, String text) {
       super(container, text);
    }
}
