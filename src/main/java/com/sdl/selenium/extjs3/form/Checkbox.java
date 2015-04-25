package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.By;
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

    public Checkbox(By...bys) {
        getPathBuilder().defaults(By.tag("input"), By.baseCls("x-form-checkbox")).init(bys);
    }

    public Checkbox(WebLocator container) {
        this(By.container(container));
    }

    public Checkbox(WebLocator container, String name) {
        this(By.container(container), By.name(name));
    }

    @Override
    public boolean isSelected(){
        return isElementPresent() && executor.isSelected(this);
    }
}