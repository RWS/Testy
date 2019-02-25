package com.sdl.selenium.web.form;

import com.sdl.selenium.web.Locator;

public class CheckBox extends Locator implements ICheck {

    public CheckBox() {
        setClassName("CheckBox");
        setTag("input");
        setType("checkbox");
    }

    public CheckBox(Locator container) {
        this();
        setContainer(container);
    }

    public CheckBox(String id) {
        this();
        setId(id);
    }

    @Override
    public boolean isSelected() {
        return ready() && executor().isSelected(this);
    }

    @Override
    public boolean clickAt() {
        return executor().clickAt(this);
    }

    @Override
    public boolean doClickAt() {
        return executor().clickAt(this);
    }

    @Override
    public boolean click() {
        return executor().click(this);
    }

    @Override
    public boolean doClick() {
        return executor().click(this);
    }

    @Override
    public boolean doubleClickAt() {
        return executor().doubleClickAt(this);
    }

    @Override
    public boolean doDoubleClickAt() {
        return executor().doubleClickAt(this);
    }
}