package com.extjs.selenium.form;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated pachage "com.extjs.selenium*" is deprecated, please use new package "com.sdl.selenium.extjs3"
 */
public class FieldSet extends com.sdl.selenium.extjs3.form.FieldSet {

    public FieldSet() {
        super();
    }

    public FieldSet(WebLocator container) {
        super(container);
    }

    public FieldSet(WebLocator container, String text) {
        super(container, text);
    }

    public FieldSet(WebLocator container, String cls, String text) {
        super(container, cls, text);
    }
}
