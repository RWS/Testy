package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.PathBuilder;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextField extends com.sdl.selenium.web.form.TextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextField.class);

              private  PathBuilder pathBuilder = getPathBuilder();
    @SafeVarargs
    public <B extends com.sdl.selenium.web.By> TextField(B... bys){
        pathBuilder.init(bys);
        pathBuilder.defaults(By.className("TextField"), By.type("text"));
    }

    @SafeVarargs
    public <B extends com.sdl.selenium.web.By> TextField(WebLocator container, B ... bys) {
        this(bys);
        pathBuilder.setContainer(container);
    }

    public TextField(WebLocator container, String label) {
        this(container, By.label(label));
    }
}