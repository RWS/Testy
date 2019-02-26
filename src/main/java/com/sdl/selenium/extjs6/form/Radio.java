package com.sdl.selenium.extjs6.form;

import com.google.common.base.Strings;
import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.WebLocator;

public class Radio extends WebLocator {

    public Radio() {
        setClassName("Radio");
        setTag("span");
        setBaseCls("x-form-radio");
        setLabelPosition("/../");
    }

    public Radio(Locator container) {
        this();
        setContainer(container);
    }

    public Radio(Locator container, String label) {
        this(container);
        setLabel(label);
    }

    public boolean isSelected() {
        WebLocator input = new WebLocator(this).setElPath("/../input");
        String areaChecked = input.getAttribute("aria-checked");
        if (Strings.isNullOrEmpty(areaChecked)) {
            input.setElPath("//input");
            String checked = input.getAttribute("checked");
            return checked != null && "true".equals(checked);
        } else {
            return "true".equals(areaChecked);
        }
    }
}