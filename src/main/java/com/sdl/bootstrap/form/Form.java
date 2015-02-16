package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.SimpleTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Form extends SimpleTextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(Form.class);

    public Form() {
        setClassName("Form");
        setTag("form");
        setTemplate("title", "count(.//legend[text()='%s']) > 0");
    }

    public Form(WebLocator container) {
        this();
        setContainer(container);
    }

    public Form(WebLocator container, String title) {
        this(container);
        setTitle(title);
    }
}