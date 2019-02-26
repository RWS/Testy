package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.web.Locator;

/**
 * defaults:
 *    - tag      : input
 *    - baseCls  : x-form-checkbox
 */
public class CheckBox extends com.sdl.selenium.web.form.CheckBox {

    public CheckBox() {
        setClassName("CheckBox");
        setTag("input");
        setBaseCls("x-form-checkbox");
    }

    public CheckBox(Locator container) {
        this();
        setContainer(container);
    }

    public CheckBox(Locator container, String name) {
        this(container);
        setName(name);
    }
}