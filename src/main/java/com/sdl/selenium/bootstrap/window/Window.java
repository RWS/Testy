package com.sdl.selenium.bootstrap.window;

import com.sdl.selenium.web.IWebLocator;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Window extends WebLocator implements IWebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Window.class);

    public Window() {
        setClassName("Window");
        setTemplate("title", "count(*[contains(@class,'-header')]//*[text()='%s']) > 0");
        setElPathSuffix("dialog-visible", "@role='dialog' and @aria-hidden='false'");
    }

    public Window(String title) {
        this();
        setTitle(title);
    }

    public String getTitleWindow() {
        WebLocator header = new WebLocator(this).setElPath("//*[contains(@class, '-header')]//h3");
        return header.getHtmlText();
    }
}