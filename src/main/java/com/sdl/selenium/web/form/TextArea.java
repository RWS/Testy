package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebLocator;

public class TextArea extends TextField {

    public TextArea() {
        withClassName("TextArea");
        withTag("textarea");
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