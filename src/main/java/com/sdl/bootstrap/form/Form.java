package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.SimpleTextField;
import org.apache.log4j.Logger;

public class Form extends SimpleTextField {
    private static final Logger logger = Logger.getLogger(Form.class);

    public Form() {
        setClassName("Form");
        setTag("form");
        setTemplates("title", "count(.//legend[text()='%s']) > 0");
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