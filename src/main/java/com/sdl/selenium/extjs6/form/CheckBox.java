package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICheck;

public class CheckBox extends Locator implements ICheck {

    public CheckBox() {
        setClassName("CheckBox");
        setBaseCls("x-form-checkbox");
    }

    public CheckBox(Locator container) {
        this();
        setContainer(container);
    }

    public CheckBox(Locator container, String label) {
        this(container);
        setLabel(label, SearchType.CONTAINS);
    }

    public CheckBox(String boxLabel, Locator container) {
        this(container);
        setLabel(boxLabel);
        setLabelPosition("/../");
    }

    @Override
    public boolean isSelected() {
        WebLocator el = new WebLocator(this).setElPath("/../input");
        String select = el.getAttribute("aria-checked");
        return select != null && select.contains("true");
    }

    @Override
    public boolean isEnabled() {
        String cls = getAttributeClass();
        return (cls != null && !cls.contains("disabled")) || getAttribute("disabled") == null;
    }

    @Override
    public boolean clickAt() {
        return false;
    }

    @Override
    public boolean doClickAt() {
        return false;
    }

    @Override
    public boolean click() {
        return false;
    }

    @Override
    public boolean doClick() {
        return false;
    }

    @Override
    public boolean doubleClickAt() {
        return false;
    }

    @Override
    public boolean doDoubleClickAt() {
        return false;
    }
}