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
        WebLocator header = new WebLocator().withClasses("x-title").withRoot("//");
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

    public Panel(WebLocator container, String title, boolean isInternationalized) {
        this(container);
        withTitle(title, isInternationalized, SearchType.EQUALS);
    }

    private WebLocator getCollapseEl(String type) {
        return new WebLocator(this).setElPath("//*[contains(@class, 'x-tool-" + type + "')]");
    }

    public boolean collapse() {
        return !getCollapseEl("collapse").isElementPresent() || getCollapseEl("collapse").click();
    }

    public boolean expand() {
        return !getCollapseEl("expand").isElementPresent() || getCollapseEl("expand").click();
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
}
