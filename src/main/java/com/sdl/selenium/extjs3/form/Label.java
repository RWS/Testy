package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Label extends ExtJsComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(Label.class);

    public Label() {
        setClassName("Label");
        setTag("label");
    }

    public Label(String text) {
        this();
        setText(text);
    }

    public Label(Locator container) {
        this();
        setContainer(container);
    }

    public Label(Locator container, String text) {
        this(text);
        setContainer(container);
    }
}
