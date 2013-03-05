package com.extjs.selenium.form;

import com.extjs.selenium.ExtJsComponent;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class Label extends ExtJsComponent {

    private static final Logger logger = Logger.getLogger(Label.class);

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
