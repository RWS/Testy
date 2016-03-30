package com.sdl.selenium.extjs6.panel;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Panel extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Panel.class);

    public Panel() {
        withClassName("Panel");
        withBaseCls("x-panel");
        WebLocator header = new WebLocator().withClasses("x-header").withRoot("/");
        withTemplateTitle(new WebLocator(header));
    }

    public Panel(WebLocator container) {
        this();
        withContainer(container);
    }

    public Panel(WebLocator container, String title) {
        this(container);
        withTitle(title, SearchType.EQUALS);
    }

    private WebLocator collapseButton = new WebLocator(this).withClasses("x-tool-collapse-right");

    public WebLocator getCollapseButton() {
        return collapseButton;
    }

    public boolean collapse() {
        return collapseButton.click();
    }

    @Override
    public Panel withVisibility(boolean visible) {
        if (visible) {
            this.withExcludeClasses("x-collapsed ", "x-panel-collapsed");
        } else {
            this.withClasses("x-collapsed ", "x-panel-collapsed");
        }
        return this;
    }

//    public static void main(String[] args) {
////        Panel panel = new Panel(null, "API Key Details");
//        Panel panel = new Panel().withTitle("API Key Details");
//        LOGGER.debug(panel.getXPath());
//    }
}
