package com.sdl.selenium.web.button;

import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.WebElement;

public class InputButton extends WebLocator implements IButton {

    public InputButton() {
        setClassName("InputButton");
        setTag("input");
        setTemplate("text", "@value=%s");
    }

    /**
     * @param container is parent of item
     */
    public InputButton(WebLocator container) {
        this();
        setContainer(container);
    }

    public InputButton(WebElement webElement) {
        this();
        setWebElement(webElement);
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