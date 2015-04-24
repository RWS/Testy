package com.sdl.selenium.bootstrap.button;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class ButtonLink extends Button {

    public ButtonLink(By ...bys) {
        getPathBuilder().defaults(By.tag("a")).init(bys);
    }

    /**
     * @param container
     */
    public ButtonLink(WebLocator container) {
        this(By.container(container));
    }

    public ButtonLink(WebLocator container, String text) {
        this(By.container(container), By.text(text, SearchType.EQUALS));
    }
}