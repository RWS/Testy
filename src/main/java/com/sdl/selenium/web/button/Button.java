package com.sdl.selenium.web.button;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Button extends WebLocator implements IButton {
    private static final Logger LOGGER = LoggerFactory.getLogger(Button.class);

    public Button() {
        withClassName("Button");
        withTag("button");
    }

    public Button(WebLocator container) {
        this();
        withContainer(container);
    }

    public Button(WebLocator container, String text, final SearchType... searchTypes) {
        this(container);
        withText(text, searchTypes);
    }
}
