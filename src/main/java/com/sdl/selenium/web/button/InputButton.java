package com.sdl.selenium.web.button;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.apache.log4j.Logger;

public class InputButton extends WebLocator implements IButton {
    private static final Logger logger = Logger.getLogger(InputButton.class);

    public InputButton() {
        setClassName("InputButton");
        setTag("input");
    }

    /**
     * @param container is parent of item
     */
    public InputButton(WebLocator container) {
        this();
        setContainer(container);
    }

    /**
     *
     * @param container is parent of item
     * @param text is value from input item
     */
    public InputButton(WebLocator container, String text) {
        this(container);
        setText(text);
    }

    // Methods

    @Override
    protected String getItemPathText() {
        return hasText() ? "@value="+ Utils.getEscapeQuotesText(getText()) : null;
    }
}