package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DisplayField extends TextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisplayField.class);

    public DisplayField(By... bys) {
        getPathBuilder().defaults(By.baseCls("x-form-display-field"), By.tag("*")).init(bys);
    }

    public DisplayField(WebLocator container) {
        this(By.container(container));
    }

    public DisplayField(WebLocator container, String label) {
        this(By.container(container), By.label(label));
    }

    public DisplayField(String name, WebLocator container) {
        this(By.container(container), By.name(name));
    }

    public boolean setValue(String value) {
        // TODO find better way (maybe extending Field not text field directly)
        LOGGER.warn("can't set Value in display field");
        return false;
    }

    public String getValue() {
        return getHtmlText();
    }
}
