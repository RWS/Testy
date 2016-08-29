package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextArea extends TextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextArea.class);

    public TextArea() {
        withClassName("TextArea");
        withTag("textarea");
    }

    public TextArea(WebLocator container) {
        this();
        withContainer(container);
    }

    public TextArea(WebLocator container, String label) {
        this(container);
        withLabel(label);
    }

    public TextArea(WebLocator container, String label, boolean isInternationalized) {
        this(container);
        withLabel(label, isInternationalized);
    }

    public TextArea(String name, WebLocator container) {
        this(container);
        withName(name);
    }
}