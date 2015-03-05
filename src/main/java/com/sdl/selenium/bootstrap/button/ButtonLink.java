package com.sdl.selenium.bootstrap.button;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class ButtonLink extends Button {

    public ButtonLink() {
        setClassName("ButtonLink");
        setBaseCls("btn");
        setTag("a");
    }

    /**
     * @param container
     */
    public ButtonLink(WebLocator container) {
        this();
        setContainer(container);
    }

    public ButtonLink(WebLocator container, String text) {
        this(container);
        setText(text);
        setSearchTextType(SearchType.EQUALS);
    }
}