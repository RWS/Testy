package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * defaults:
 *    - tag      : input
 *    - baseCls  : x-form-checkbox
 */
public class CheckBox extends ExtJsComponent implements ICheck {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckBox.class);

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

    @Override
    public boolean isSelected(){
        return executor.isSelected(this);
    }
}