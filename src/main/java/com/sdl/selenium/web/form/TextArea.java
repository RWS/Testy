package com.sdl.selenium.web.form;

import com.sdl.selenium.web.Locator;

public class TextArea extends TextField {

    public TextArea() {
        setClassName("TextArea");
        setTag("textarea");
    }

    public TextArea(Locator container) {
        this();
        setContainer(container);
    }

    public TextArea(Locator container, String label) {
        this(container);
        setLabel(label);
    }
}