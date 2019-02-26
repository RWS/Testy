package com.sdl.selenium.bootstrap.window;

import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class Window extends Locator {

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
        WebLocator header = new WebLocator(this).setElPath("//*[contains(@class, '-header')]//h3");
        return header.getText();
    }
}