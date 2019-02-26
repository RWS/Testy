package com.sdl.selenium.web.button;

import com.sdl.selenium.web.Locator;

public class InputButton extends Button {

    public InputButton() {
        setClassName("InputButton");
        setTag("input");
        setTemplate("text", "@value=%s");
    }

    /**
     * @param container is parent of item
     */
    public InputButton(Locator container) {
        this();
        setContainer(container);
    }

    /**
     * @param container is parent of item
     * @param text      is value from input item
     */
    public InputButton(Locator container, String text) {
        this(container);
        setText(text);
    }
}