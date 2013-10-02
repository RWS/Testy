package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.SimpleTextField;
import org.apache.log4j.Logger;

public class TextField extends SimpleTextField {
    private static final Logger logger = Logger.getLogger(TextField.class);

    public TextField() {
        setClassName("TextField");
        setTag("input");
        setType("text");
    }

    public TextField(WebLocator container) {
        this();
        setContainer(container);
    }

    public TextField(WebLocator container, String label) {
        this(container);
        setLabel(label);
    }
}