package com.sdl.selenium.extjs4.form;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICheck;

/**
 * defaults:
 *    - tag      : input
 *    - baseCls  : x-form-checkbox
 */
public class CheckBox extends WebLocator implements ICheck {

    public CheckBox() {
        setClassName("CheckBox");
        setTag("input");
        setBaseCls("x-form-checkbox");
    }

    public CheckBox(WebLocator container) {
        this();
        setContainer(container);
    }

    public CheckBox(WebLocator container, String name) {
        this(container);
        setName(name);
    }

    @Deprecated
    public boolean isSelected(){
        return isChecked();
    }

    @Override
    public boolean isChecked() {
        return executor.isSelected(this);
    }
}