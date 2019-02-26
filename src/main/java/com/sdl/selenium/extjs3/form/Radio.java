package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.SearchType;

public class Radio extends Locator {

    public Radio() {
        setClassName("Radio");
        setTag("input");
        setBaseCls("x-form-radio");
        setLabelPosition("/../");
        setTemplate("text", "@value=%s");
    }

    public Radio(String value) {
        this();
        setText(value, SearchType.EQUALS);
    }

    public Radio(Locator container) {
        this();
        setContainer(container);
    }

    public Radio(Locator container, String name) {
        this(container);
        setName(name);
    }

    public Radio(String label, Locator container) {
        this(container);
        setLabel(label);
    }

    public boolean isSelected() {
        return isElementPresent() && executor().isSelected(this);
    }
}