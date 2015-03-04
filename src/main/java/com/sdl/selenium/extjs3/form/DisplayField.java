package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DisplayField extends TextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisplayField.class);

    public DisplayField() {
        setClassName("DisplayField");
        setBaseCls("x-form-display-field");
        setTag("*");
    }

    public DisplayField(WebLocator container) {
        this();
        setContainer(container);
    }

    public DisplayField(WebLocator container, String label) {
        this(container);
        setLabel(label);
    }

    public DisplayField(String name, WebLocator container) {
        this(container);
        setName(name);
    }

    public boolean setValue(String value) {
        // TODO find better way (maybe extending Field not text field directly)
        LOGGER.warn("can't set Value in display field");
        return false;
    }


    public String getValue() {
        String value = "";
        if(ready()){
            value = getHtmlText();
        } else {
            LOGGER.warn("getValue : field is not ready for use: " + toString());
        }
        return value;
    }
}
