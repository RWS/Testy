package com.extjs.selenium.form;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated pachage "com.extjs.selenium*" is deprecated, please use new package "com.sdl.selenium.extjs3"
 */
public class DisplayField extends com.sdl.selenium.extjs3.form.DisplayField {

    public DisplayField() {
        super();
    }

    public DisplayField(WebLocator container) {
        super(container);
    }

    public DisplayField(WebLocator container, String label) {
        super(container, label);
    }

    public DisplayField(String name, WebLocator container) {
        super(name, container);
    }
}
