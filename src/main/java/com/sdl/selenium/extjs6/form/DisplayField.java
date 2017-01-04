package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DisplayField extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisplayField.class);

    public DisplayField() {
        withClassName("DisplayField");
        withBaseCls("x-form-display-field");
        withTag("div");
    }

    public DisplayField(WebLocator container) {
        this();
        withContainer(container);
    }

    public DisplayField(WebLocator container, String label) {
        this(container);
        withLabel(label, SearchType.DEEP_CHILD_NODE_OR_SELF);
    }
}
