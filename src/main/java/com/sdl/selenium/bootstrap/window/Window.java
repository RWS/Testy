package com.sdl.selenium.bootstrap.window;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.IWebLocator;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Window extends WebLocator implements IWebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Window.class);

    public Window(By ...bys) {
        getPathBuilder().defaults(By.template("title", "count(*[contains(@class,'-header')]//*[text()='%s']) > 0"), By.pathSuffix("dialog-visible", "@role='dialog' and @aria-hidden='false'")).init(bys);
    }

    public Window(String title) {
        this(By.title(title));
    }

    public String getTitleWindow() {
        WebLocator header = new WebLocator(By.container(this), By.xpath("//*[contains(@class, '-header')]//h3"));
        return header.getHtmlText();
    }
}