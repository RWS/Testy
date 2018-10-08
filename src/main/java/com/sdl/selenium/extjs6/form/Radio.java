package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.WebLocator;

public class Radio extends WebLocator {

    public Radio() {
        setClassName("Radio");
        setTag("span");
        setBaseCls("x-form-radio");
        setLabelPosition("/../");
    }

    public Radio(WebLocator container) {
        this();
        setContainer(container);
    }

    public Radio(WebLocator container, String label) {
        this(container);
        setLabel(label);
    }

    public boolean isSelected() {
        WebLocator input = new WebLocator(this).setElPath("/../input");
        String checked = input.getAttribute("aria-checked");
        return checked != null && "true".equals(checked);
    }
}