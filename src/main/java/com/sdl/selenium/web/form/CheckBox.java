package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebLocator;

public class CheckBox extends WebLocator implements ICheck {

    public CheckBox() {
        withClassName("CheckBox");
        withTag("input");
        withType("checkbox");
    }

    public CheckBox(WebLocator container) {
        this();
        withContainer(container);
    }

    public CheckBox(String id) {
        this();
        withId(id);
    }

    @Override
    public boolean isSelected() {
        return ready() && executor.isSelected(this);
    }
}