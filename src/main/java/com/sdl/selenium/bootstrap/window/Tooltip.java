package com.sdl.selenium.bootstrap.window;

import com.sdl.selenium.web.WebLocator;

public class Tooltip extends WebLocator {

    public Tooltip() {
        setInfoMessage("Tooltip");
        setClasses("tooltip");
        setTemplate("title", "count(//*[@class='tooltip-inner' and text()=%s]) > 0");
    }

    public Tooltip(String message) {
        this();
        setTitle(message);
        setInfoMessage("Tooltip has message: '" + message + "'.");
    }
}