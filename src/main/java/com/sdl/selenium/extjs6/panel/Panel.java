package com.sdl.selenium.extjs6.panel;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Panel extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Panel.class);

    public Panel() {
        setClassName("Panel");
        setBaseCls("x-panel");
        WebLocator header = new WebLocator().setClasses("x-header").setRoot("/");
        setTemplateTitle(new WebLocator(header));
    }

    public Panel(WebLocator container) {
        this();
        setContainer(container);
    }

    public Panel(WebLocator container, String title) {
        this(container);
        setTitle(title, SearchType.EQUALS);
    }

    private WebLocator collapseButton = new WebLocator(this).setClasses("x-tool-collapse-right");

    public WebLocator getCollapseButton() {
        return collapseButton;
    }

    public boolean collapse() {
        return collapseButton.click();
    }

    @Override
    public Panel setVisibility(boolean visible) {
        if (visible) {
            this.setExcludeClasses("x-collapsed ", "x-panel-collapsed");
        } else {
            this.setClasses("x-collapsed ", "x-panel-collapsed");
        }
        return this;
    }

//    public static void main(String[] args) {
////        Panel panel = new Panel(null, "API Key Details");
//        Panel panel = new Panel().setTitle("API Key Details");
//        LOGGER.debug(panel.getXPath());
//    }
}
