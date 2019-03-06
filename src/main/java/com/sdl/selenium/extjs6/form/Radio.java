package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.utils.config.WebLocatorConfig;
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
        WebLocator input = new WebLocator(this);
        String checked;
        if ("6.7.0".equals(WebLocatorConfig.getExtJsVersion())) {
            input.setElPath("//input");
            checked = input.getAttribute("checked");
        } else {
            input.setElPath("/../input");
            checked = input.getAttribute("aria-checked");
        }
        return checked != null && "true".equals(checked);
    }
}