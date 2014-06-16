package com.sdl.selenium.web.test;

import org.apache.log4j.Logger;

public class TextField extends WebLocator {
    private static final Logger LOGGER = Logger.getLogger(TextField.class);

    private PathBuilder pathBuilder;

    public TextField(By... by) {
        pathBuilder = new PathBuilder(by);
        setPathBuilder(pathBuilder);
    }

    public TextField(WebLocator container, By... by) {
        this(by);
        pathBuilder.setContainer(container);
    }
}
