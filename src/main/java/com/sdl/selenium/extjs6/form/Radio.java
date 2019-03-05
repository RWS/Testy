package com.sdl.selenium.extjs6.form;

import com.google.common.base.Strings;
import com.sdl.selenium.web.SearchType;
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

    public Radio(WebLocator container, String label, SearchType... searchTypes) {
        this(container);
        setLabel(label, searchTypes);
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