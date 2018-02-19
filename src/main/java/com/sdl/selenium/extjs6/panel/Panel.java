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
        WebLocator header = new WebLocator().setClasses("x-title").setRoot("//");
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

    private WebLocator getCollapseEl(String type) {
        return new WebLocator(this).setClasses("x-tool-" + type);
    }

    public boolean collapse() {
        return !getCollapseEl("collapse").isElementPresent() || getCollapseEl("collapse").click();
    }

    public boolean expand() {
        return !getCollapseEl("expand").isElementPresent() || getCollapseEl("expand").click();
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
}
