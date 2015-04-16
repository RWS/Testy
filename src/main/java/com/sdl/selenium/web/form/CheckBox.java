package com.sdl.selenium.web.form;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebLocator;

public class CheckBox extends WebLocator implements ICheck {

    public CheckBox(By...bys) {
        getPathBuilder().defaults(By.tag("input"), By.type("checkbox")).init(bys);
    }

    public CheckBox(WebLocator container) {
        this();
        getPathBuilder().setContainer(container);
    }

    public CheckBox(String id) {
        this(By.id(id));
    }

    @Override
    public boolean isSelected() {
        return ready() && executor.isSelected(this);
    }
}