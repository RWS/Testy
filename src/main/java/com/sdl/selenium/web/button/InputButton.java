package com.sdl.selenium.web.button;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputButton extends WebLocator implements IButton {
    private static final Logger LOGGER = LoggerFactory.getLogger(InputButton.class);

    public InputButton(By ...bys) {
        getPathBuilder().defaults(By.tag("input"), By.template("text", "@value='%s'")).init(bys);
    }

    /**
     * @param container is parent of item
     */
    public InputButton(WebLocator container) {
        this();
        getPathBuilder().setContainer(container);
    }

    /**
     * @param container is parent of item
     * @param text      is value from input item
     */
    public InputButton(WebLocator container, String text) {
        this(By.container(container), By.text(text));
    }
}