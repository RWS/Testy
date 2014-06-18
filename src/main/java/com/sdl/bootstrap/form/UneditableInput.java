package com.sdl.bootstrap.form;

import com.sdl.selenium.web.IText;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class UneditableInput extends WebLocator implements IText {
    private static final Logger LOGGER = Logger.getLogger(UneditableInput.class);

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