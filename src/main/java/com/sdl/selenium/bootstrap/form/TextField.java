package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextField extends com.sdl.selenium.web.form.TextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextField.class);

    public TextField() {
        withClassName("TextField");
        withType("text"); // TODO try to move in TextField
    }

    public TextField(WebLocator container) {
        this();
        withContainer(container);
    }

    public TextField(WebLocator container, String label) {
        this(container);
        withLabel(label);
    }

    public TextField(WebLocator container, String label, boolean isInternationalized) {
        this(container);
        withLabel(label, isInternationalized);
    }
}