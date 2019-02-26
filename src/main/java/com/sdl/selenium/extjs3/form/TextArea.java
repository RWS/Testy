package com.sdl.selenium.extjs3.form;

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

    public TextArea(String name, Locator container) {
        this(container);
        setName(name);
    }
}