package com.sdl.selenium.bootstrap.button;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class ButtonLink extends Button {

    public ButtonLink() {
        withClassName("ButtonLink");
        withBaseCls("btn");
        withTag("a");
    }

    /**
     * @param container parent's element
     */
    public ButtonLink(WebLocator container) {
        this();
        withContainer(container);
    }

    public ButtonLink(WebLocator container, String text) {
        this(container);
        withText(text);
        withSearchTextType(SearchType.EQUALS);
    }
}