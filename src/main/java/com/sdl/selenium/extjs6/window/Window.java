package com.sdl.selenium.extjs6.window;

import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Window extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Window.class);

    public Window() {
        super();
    }

    public Window(String title) {
        super(title);
    }
}