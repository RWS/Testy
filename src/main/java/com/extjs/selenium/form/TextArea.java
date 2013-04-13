package com.extjs.selenium.form;

import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class TextArea extends TextField {
    private static final Logger logger = Logger.getLogger(TextArea.class);

    public TextArea() {
        setClassName("TextArea");
        setTag("textarea");
    }

    public TextArea(WebLocator container, String label) {
        this();
        setContainer(container);
        setLabel(label);
    }

    public TextArea(String name, WebLocator container) {
        this();
        setContainer(container);
        setName(name);
    }
}