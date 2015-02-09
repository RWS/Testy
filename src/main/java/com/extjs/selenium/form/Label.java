package com.extjs.selenium.form;

import com.extjs.selenium.ExtJsComponent;
import com.sdl.selenium.web.WebLocator;
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

    public Label(WebLocator container) {
        this();
        setContainer(container);
    }

    public Label(WebLocator container, String text) {
        this(text);
        setContainer(container);
    }
}
