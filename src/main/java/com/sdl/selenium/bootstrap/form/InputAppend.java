package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputAppend extends TextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(InputAppend.class);

    public InputAppend() {
        withClassName("InputAppend");
        withType("text");
    }

    public InputAppend(WebLocator container) {
        this();
        withContainer(container);
    }

    public InputAppend(WebLocator container, String label) {
        this(container);
        withLabel(label);
    }

    /**
     * click on checkbox
     * @return true | false
     */
    public boolean append() {
        WebLocator appendEl = new WebLocator(this).withElxPath("//following-sibling::*").withInfoMessage("append");
        return appendEl.click();
    }
}