package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.IText;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UneditableInput extends WebLocator implements IText {
    private static final Logger LOGGER = LoggerFactory.getLogger(UneditableInput.class);

    public UneditableInput() {
        setClassName("UneditableInput");
        setBaseCls("uneditable-input");
        setTag("span");
    }

    public UneditableInput(WebLocator container) {
        this();
        setContainer(container);
    }

    public UneditableInput(WebLocator container, String label) {
        this(container);
        setLabel(label);
    }
}