package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.by.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextField extends com.sdl.selenium.web.form.TextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextField.class);

    public TextField(com.sdl.selenium.web.By... bys){
        getPathBuilder().init(bys);
        getPathBuilder().defaults(By.type("text"));
    }

    public TextField(WebLocator container) {
        this();
        getPathBuilder().setContainer(container);
    }

    public TextField(WebLocator container, String label) {
        this(By.container(container), By.label(label));
    }
}