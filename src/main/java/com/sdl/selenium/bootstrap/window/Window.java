package com.sdl.selenium.bootstrap.window;

import com.sdl.selenium.web.IWebLocator;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Window extends WebLocator implements IWebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Window.class);

    public Window() {
        setClassName("Window");
//        setTemplate("title", "count(*[contains(@class,'-header')]//*[text()='%s']) > 0"); //TODO make sure setTemplate Title is working
        WebLocator header = new WebLocator().setClasses("modal-header");
        setTemplateTitle(new WebLocator(header));
        setAttribute("role", "dialog");
        setAttribute("aria-hidden", "false");
    }

    public Window(String title) {
        this();
        setTitle(title, SearchType.EQUALS);
    }

    public String getTitleWindow() {
        WebLocator header = new WebLocator(this).setElxPath("//*[contains(@class, '-header')]//h3");
        return header.getText();
    }
}