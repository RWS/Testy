package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DisplayField extends TextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisplayField.class);

    public DisplayField() {
        withClassName("DisplayField");
        withBaseCls("x-form-display-field");
        withTag("*");
    }

    public DisplayField(WebLocator container) {
        this();
        withContainer(container);
    }

    public DisplayField(WebLocator container, String label) {
        this(container);
        withLabel(label);
    }

    public DisplayField(WebLocator container, String label, boolean isInternationalized) {
        this(container);
        withLabel(label, isInternationalized);
    }

    public DisplayField(String name, WebLocator container) {
        this(container);
        withName(name);
    }

    public boolean setValue(String value) {
        // TODO find better way (maybe extending Field not text field directly)
        LOGGER.warn("can't set Value in display field");
        return false;
    }


    public String getValue() {
        String value = "";
        if(ready()){
            value = getText();
        } else {
            LOGGER.warn("getValue : field is not ready for use: " + toString());
        }
        return value;
    }
}
