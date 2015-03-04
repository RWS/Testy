package com.extjs.selenium.form;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated pachage "com.extjs.selenium*" is deprecated, please use new package "com.sdl.selenium.extjs3"
 */
public class ComboBox extends com.sdl.selenium.extjs3.form.ComboBox {

    public ComboBox() {
        super();
    }

    public ComboBox(String cls) {
        super(cls);
    }

    public ComboBox(WebLocator container) {
        super(container);
    }

    public ComboBox(WebLocator container, String label) {
       super(container, label);
    }

    public ComboBox(String name, WebLocator container) {
        super(name, container);
    }

    public ComboBox(WebLocator container, String cls, String name, boolean hasName) {
       super(container, cls, name, hasName);
    }
}