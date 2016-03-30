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
public class Checkbox extends ExtJsComponent implements ICheck {
    private static final Logger LOGGER = LoggerFactory.getLogger(Checkbox.class);

    public Checkbox() {
        withClassName("Checkbox");
        withTag("input");
        withBaseCls("x-form-checkbox");
    }

    public Checkbox(WebLocator container) {
        this();
        withContainer(container);
    }

    public Checkbox(WebLocator container, String name) {
        this(container);
        withName(name);
    }

    @Override
    public boolean isSelected(){
        return isElementPresent() && executor.isSelected(this);
    }
}