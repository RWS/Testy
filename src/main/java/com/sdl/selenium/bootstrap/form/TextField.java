package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextField extends com.sdl.selenium.web.form.TextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextField.class);

    public TextField(By...bys){
        getPathBuilder().defaults(By.type("text")).init(bys);
    }

    public TextField(WebLocator container) {
        this(By.container(container));
    }

    public TextField(WebLocator container, String label) {
        this(By.container(container), By.label(label));
    }
}