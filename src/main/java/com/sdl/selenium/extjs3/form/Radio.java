package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Radio extends ExtJsComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(Radio.class);

    public Radio() {
        withClassName("Radio");
        withTag("input");
        withBaseCls("x-form-radio");
        withLabelPosition("/../");
        withTemplate("text", "@value='%s'");
    }

    public Radio(String value) {
        this();
        withText(value);
    }

    public Radio(WebLocator container) {
        this();
        withContainer(container);
    }

    public Radio(WebLocator container, String name) {
        this(container);
        withName(name);
    }

    public Radio(String label, WebLocator container) {
        this(container);
        withLabel(label);
    }

    public boolean isSelected() {
        return isElementPresent() && executor.isSelected(this);
    }
}