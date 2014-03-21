package com.sdl.bootstrap.window;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.IWebLocator;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class Window extends WebLocator implements IWebLocator {
    private static final Logger logger = Logger.getLogger(Window.class);

    public Window() {
        setClassName("Window");
    }

    public Window(String title) {
        this();
        setTitle(title);
        setElPathSuffix(" and count(*[contains(@class,'-header')]//*[text()='" + getTitle() + "']) > 0");
    }

    public String getItemPath(boolean disabled) {
        String selector = getBasePathSelector();
        selector = Utils.fixPathSelector(selector);
        return "".equals(selector) ? "//*[@role='dialog' and @aria-hidden='false']" : "//*[@role='dialog' and @aria-hidden='false' and " + selector + "]";
    }

    public String getTitleWindow() {
        return new WebLocator(this, "//*[contains(@class, '-header')]//h3").getHtmlText();
    }
}