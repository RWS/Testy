package com.sdl.selenium.bootstrap.window;

import com.sdl.selenium.web.IWebLocator;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Window extends WebLocator implements IWebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Window.class);

    public Window() {
        withClassName("Window");
//        withTemplate("title", "count(*[contains(@class,'-header')]//*[text()='%s']) > 0"); //TODO make sure withTemplate Title is working
        WebLocator header = new WebLocator().withClasses("modal-header");
        withTemplateTitle(new WebLocator(header));
        withAttribute("role", "dialog");
        withAttribute("aria-hidden", "false");
    }

    public Window(String title) {
        this();
        withTitle(title, SearchType.EQUALS);
    }

    public Window(String title, boolean isInternationalized) {
        this();
        withTitle(title, isInternationalized, SearchType.EQUALS);
    }

    public String getTitleWindow() {
        WebLocator header = new WebLocator(this).withElxPath("//*[contains(@class, '-header')]//h3");
        return header.getText();
    }
}