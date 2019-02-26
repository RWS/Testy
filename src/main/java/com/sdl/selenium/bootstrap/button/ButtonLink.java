package com.sdl.selenium.bootstrap.button;

import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.SearchType;

public class ButtonLink extends Button {

    public ButtonLink() {
        setClassName("ButtonLink");
        setBaseCls("btn");
        setTag("a");
    }

    /**
     * @param container parent's element
     */
    public ButtonLink(Locator container) {
        this();
        setContainer(container);
    }

    public ButtonLink(Locator container, String text) {
        this(container);
        setText(text);
        setSearchTextType(SearchType.EQUALS);
    }
}