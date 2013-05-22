package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.SimpleTextField;
import org.apache.log4j.Logger;

public class TextArea extends SimpleTextField {
    private static final Logger logger = Logger.getLogger(TextArea.class);

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