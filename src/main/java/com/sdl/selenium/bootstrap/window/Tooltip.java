package com.sdl.selenium.bootstrap.window;

import com.sdl.selenium.web.WebLocator;

public class Tooltip extends WebLocator {

    public Tooltip() {
        withInfoMessage("Tooltip");
        withClasses("tooltip");
        withTemplate("title", "count(//*[@class='tooltip-inner' and text()='%s']) > 0");
    }

    public Tooltip(String message) {
        this();
        withTitle(message);
        withInfoMessage("Tooltip has message: '" + message + "'.");
    }

    public Tooltip(String message, boolean isInternationalized) {
        this();
        withTitle(message, isInternationalized);
        withInfoMessage("Tooltip has message: '" + message + "'.");
    }
}