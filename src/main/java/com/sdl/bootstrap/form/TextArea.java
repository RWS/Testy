package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.SimpleTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextArea extends SimpleTextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextArea.class);

    public TextArea() {
        setClassName("TextArea");
        setTag("textarea");
    }

    public TextArea(WebLocator container) {
        this();
        setContainer(container);
    }

    public TextArea(WebLocator container, String label) {
        this(container);
        setLabel(label);
    }
}