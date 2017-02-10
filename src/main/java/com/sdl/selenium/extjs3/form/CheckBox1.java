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
public class CheckBox1 extends ExtJsComponent implements ICheck {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckBox1.class);

    public CheckBox1() {
        setClassName("CheckBox1");
        setTag("input");
        setBaseCls("x-form-checkbox");
    }

    public CheckBox1(WebLocator container) {
        this();
        setContainer(container);
    }

    public CheckBox1(WebLocator container, String name) {
        this(container);
        setName(name);
    }

    @Override
    public boolean isSelected(){
        return isElementPresent() && executor.isSelected(this);
    }
}