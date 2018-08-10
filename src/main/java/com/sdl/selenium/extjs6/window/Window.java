package com.sdl.selenium.extjs6.window;

import com.sdl.selenium.web.SearchType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Window extends com.sdl.selenium.extjs4.window.Window {
    private static final Logger LOGGER = LoggerFactory.getLogger(Window.class);

    public Window() {
        super();
    }

    public Window(String title, SearchType... searchTypes) {
        this();
        setTitle(title, searchTypes);
    }
}