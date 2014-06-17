package com.sdl.selenium.web.test;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.PathBuilder;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class TextField extends WebLocator {
    private static final Logger LOGGER = Logger.getLogger(TextField.class);

    private PathBuilder pathBuilder;

    public TextField(By... by) {
        pathBuilder = new PathBuilder(this, by);
        setPathBuilder(pathBuilder);
    }

    public TextField(WebLocator container, By... by) {
        this(by);
        pathBuilder.setContainer(container);
    }
}
