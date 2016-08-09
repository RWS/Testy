package com.sdl.selenium.web.button;

import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputButton extends WebLocator implements IButton {
    private static final Logger LOGGER = LoggerFactory.getLogger(InputButton.class);

    public InputButton() {
        withClassName("InputButton");
        withTag("input");
        withTemplate("text", "@value='%s'");
    }

    /**
     * @param container is parent of item
     */
    public InputButton(WebLocator container) {
        this();
        withContainer(container);
    }

    /**
     * @param container is parent of item
     * @param text      is value from input item
     */
    public InputButton(WebLocator container, String text) {
        this(container);
        withText(text);
    }

    public InputButton(WebLocator container, String text, boolean isInternationalized) {
        this(container);
        withText(text, isInternationalized);
    }
}