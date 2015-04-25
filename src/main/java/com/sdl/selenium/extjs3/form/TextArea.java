package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextArea extends TextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextArea.class);

    public TextArea(By...bys) {
        getPathBuilder().defaults(By.tag("textarea")).init(bys);
    }

    public TextArea(WebLocator container) {
        this(By.container(container));
    }

    public TextArea(WebLocator container, String label) {
        this(By.container(container), By.label(label));
    }

    public TextArea(String name, WebLocator container) {
        this(By.container(container), By.name(name));
    }
}