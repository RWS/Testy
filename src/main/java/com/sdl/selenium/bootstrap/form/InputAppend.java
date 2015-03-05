package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.SimpleTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputAppend extends SimpleTextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(InputAppend.class);

    public InputAppend() {
        setClassName("InputAppend");
        setType("text");
    }

    public InputAppend(WebLocator container) {
        this();
        setContainer(container);
    }

    public InputAppend(WebLocator container, String label) {
        this(container);
        setLabel(label);
    }

    /**
     * click pe buttonul bifa
     */
    public boolean append() {
        WebLocator appendEl = new WebLocator(this, "//following-sibling::*").setInfoMessage("append");
        return appendEl.click();
    }
}