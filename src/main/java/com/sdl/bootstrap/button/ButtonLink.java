package com.sdl.bootstrap.button;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class ButtonLink extends Button {
    private static final Logger logger = Logger.getLogger(ButtonLink.class);

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