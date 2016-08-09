package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.IText;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UneditableInput extends WebLocator implements IText {
    private static final Logger LOGGER = LoggerFactory.getLogger(UneditableInput.class);

    public UneditableInput() {
        withClassName("UneditableInput");
        withBaseCls("uneditable-input");
        withTag("span");
    }

    public UneditableInput(WebLocator container) {
        this();
        withContainer(container);
    }

    public UneditableInput(WebLocator container, String label) {
        this(container);
        withLabel(label);
    }

    public UneditableInput(WebLocator container, String label, boolean isInternationalized) {
        this(container);
        withLabel(label, isInternationalized);
    }
}