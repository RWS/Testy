package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Radio extends ExtJsComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(Radio.class);

    public Radio() {
        setClassName("Radio");
        setTag("input");
        setBaseCls("x-form-radio");
        setLabelPosition("/../");
    }

    public Radio(String value) {
        this();
        setAttribute("value", value, SearchType.EQUALS);
    }

    public Radio(WebLocator container) {
        this();
        setContainer(container);
    }

    public Radio(WebLocator container, String name) {
        this(container);
        setName(name);
    }

    public Radio(String label, WebLocator container) {
        this(container);
        setLabel(label);
    }

    public boolean isSelected() {
        return isElementPresent() && executor.isSelected(this);
    }
}