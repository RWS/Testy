package com.extjs.selenium.form;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated pachage "com.extjs.selenium*" is deprecated, please use new package "com.sdl.selenium.extjs3"
 */
public class DateField extends com.sdl.selenium.extjs3.form.DateField {

    public DateField() {
        super();
    }

    public DateField(WebLocator container) {
        super(container);
    }

    public DateField(WebLocator container, String cls) {
        super(container, cls);
    }

    public DateField(String name, WebLocator container) {
        super(name, container);
    }
}
