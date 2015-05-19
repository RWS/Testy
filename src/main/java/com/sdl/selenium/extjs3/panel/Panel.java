package com.sdl.selenium.extjs3.panel;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Panel extends ExtJsComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(Panel.class);

    private String headerBaseCls;

    public Panel() {
        setClassName("Panel");
        setBaseCls("x-panel");
        setHeaderBaseCls(getBaseCls());
        setElPathSuffix("exclude-hide-cls", "not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))");
        setTemplate("title", "count(*[contains(@class,'" + getHeaderBaseCls() + "-header') or contains(@class, '-tl')]//*[text()='%s']) > 0");
    }

    public Panel(String title) {
        this();
        setTitle(title);
    }

    public Panel(WebLocator container) {
        this();
        setContainer(container);
    }

    public Panel(WebLocator container, String title) {
        this(container);
        setTitle(title);
    }

    public Panel(String cls, WebLocator container, String excludeClass) {
        this(container);
        setClasses(cls);
        setExcludeClasses(excludeClass);
    }

    public String getHeaderBaseCls() {
        return headerBaseCls;
    }

    public void setHeaderBaseCls(String headerBaseCls) {
        this.headerBaseCls = headerBaseCls;
    }

    public String itemToString() {
        String info;
        if (hasTitle()) {
            info = getTitle();
        } else {
            info = super.itemToString();
        }
        return info;
    }

    public ExtJsComponent getBodyComponent() {
        return new ExtJsComponent(this, "//*[contains(@class, '" + getBaseCls() + "-body')]");
    }

    public boolean clickOnTool(String id) {
        WebLocator toolElement = new WebLocator(this).setClasses("x-tool-" + id).setVisibility(true);
        toolElement.setInfoMessage("x-tool-" + id);
        return toolElement.click();
    }

    public boolean close() {
        boolean closed = clickOnTool("close");
        if (closed) {
            Utils.sleep(50);
        }
        return closed;
    }

    public boolean maximize() {
        boolean isMaximized = isMaximized();
        boolean maximized = isMaximized || clickOnTool("maximize");
        if (!isMaximized && maximized) {
            Utils.sleep(50);
        }
        return maximized;
    }

    public boolean isMaximized() {
        WebLocator maximizeTool = new WebLocator(this).setClasses("x-tool-maximize");
        return !maximizeTool.isVisible();
    }

    public boolean restore() {
        return clickOnTool("restore");
    }

    public boolean minimize() {
        return clickOnTool("minimize");
    }

    public boolean toggle() {
        return clickOnTool("toggle");
    }
}
