package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Label extends ExtJsComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(Label.class);

    public Label(By...bys) {
        getPathBuilder().defaults(By.tag("label")).init(bys);
    }

    public Label(String text) {
        this(By.text(text));
    }

    public Label(WebLocator container) {
        this(By.container(container));
    }

    public Label(WebLocator container, String text) {
        this(By.container(container), By.text(text));
    }
}
