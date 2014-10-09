package com.sdl.selenium.web.button;

import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputButton extends WebLocator implements IButton {
    private static final Logger logger = LoggerFactory.getLogger(InputButton.class);

    public InputButton() {
        setClassName("InputButton");
        setTag("input");
        setTemplate("text", "@value='%s'");
    }

    /**
     * @param container is parent of item
     */
    public InputButton(WebLocator container) {
        this();
        setContainer(container);
    }

    /**
     * @param container is parent of item
     * @param text      is value from input item
     */
    public InputButton(WebLocator container, String text) {
        this(container);
        setText(text);
    }
}