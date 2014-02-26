package com.extjs.selenium.form;

import com.extjs.selenium.ExtJsComponent;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICheck;
import org.apache.log4j.Logger;

/**
 * defaults:
 *    - tag      : input
 *    - baseCls  : x-form-checkbox
 */
public class Checkbox extends ExtJsComponent implements ICheck {

    private static final Logger logger = Logger.getLogger(Checkbox.class);

    public Checkbox() {
        setClassName("Checkbox");
        setTag("input");
        setBaseCls("x-form-checkbox");
    }

    public Checkbox(WebLocator container) {
        this();
        setContainer(container);
    }

    public Checkbox(WebLocator container, String name) {
        this(container);
        setName(name);
    }

    @Override
    public boolean isSelected(){
        return isElementPresent() && executor.isSelected(this);
    }
}