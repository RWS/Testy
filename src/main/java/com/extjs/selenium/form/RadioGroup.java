package com.extjs.selenium.form;

import com.sdl.selenium.extjs3.form.Radio;
import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated pachage "com.extjs.selenium*" is deprecated, please use new package "com.sdl.selenium.extjs3"
 */
public class RadioGroup extends com.sdl.selenium.extjs3.form.RadioGroup {

    private com.sdl.selenium.extjs3.form.Radio radio = new Radio(this);

    public RadioGroup() {
        super();
    }

    public RadioGroup(WebLocator container) {
        super(container);
    }

    public RadioGroup(WebLocator container, String name) {
        super(container, name);
    }
}