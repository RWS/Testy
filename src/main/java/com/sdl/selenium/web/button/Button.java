package com.sdl.selenium.web.button;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class Button extends WebLocator implements IButton {

    public Button() {
        setClassName("Button");
        setTag("button");
    }

    public Button(WebLocator container) {
        this();
        setContainer(container);
    }

    public Button(WebLocator container, String text, final SearchType... searchTypes) {
        this(container);
        setText(text, searchTypes);
    }
}
