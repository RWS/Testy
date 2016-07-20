package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.WebLocatorAbstractBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Radio extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Radio.class);

    public Radio() {
        withClassName("Radio");
        withTag("span");
        withBaseCls("x-form-radio");
        withLabelPosition("/../");
    }

    public Radio(WebLocator container) {
        this();
        withContainer(container);
    }

    public Radio(WebLocator container, String label) {
        this(container);
        withLabel(label);
    }

    public boolean isSelected() {
        WebLocator input = new WebLocator(this).setElPath("/../input");
        String checked = input.getAttribute("aria-checked");
        return checked != null && "true".equals(checked);
    }
}