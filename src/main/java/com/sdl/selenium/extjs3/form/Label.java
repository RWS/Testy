package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Label extends ExtJsComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(Label.class);

    public Label() {
        withClassName("Label");
        withTag("label");
    }

    public Label(String text) {
        this();
        withText(text);
    }

    public Label(WebLocator container) {
        this();
        withContainer(container);
    }

    public Label(WebLocator container, String text) {
        this(text);
        withContainer(container);
    }
}
