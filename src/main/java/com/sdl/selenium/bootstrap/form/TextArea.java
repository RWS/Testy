package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.WebLocator;

public class TextArea extends com.sdl.selenium.web.form.TextArea {

    public TextArea() {
    }

    public TextArea(WebLocator container) {
        this();
        withContainer(container);
    }

    public TextArea(WebLocator container, String label) {
        this(container);
        withLabel(label);
    }

    public TextArea(WebLocator container, String label, boolean isInternationalized) {
        this(container);
        withLabel(label, isInternationalized);
    }
}