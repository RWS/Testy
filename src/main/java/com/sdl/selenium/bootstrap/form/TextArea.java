package com.sdl.selenium.bootstrap.form;

public class TextArea extends com.sdl.selenium.web.form.TextArea {

    public TextArea() {
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