package com.sdl.selenium.extjs3.panel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;

public class Panel extends ExtJsComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(Panel.class);

    private String headerBaseCls;

    public Panel() {
        setClassName("Panel");
        setBaseCls("x-panel");
        setHeaderBaseCls(getPathBuilder().getBaseCls());
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

    public ExtJsComponent getBodyComponent() {
        return new ExtJsComponent(this, "//*[contains(@class, '" + getPathBuilder().getBaseCls() + "-body')]");
    }

    /**
     * click on element with class "x-tool-" + id
     * @param id
     * @return
     */
    public boolean clickOnTool(String id) {
        ExtJsComponent toolElement = getToolElement(id).setVisibility(true);
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
        ExtJsComponent maximizeTool = getToolElement("maximize");
        return !maximizeTool.isVisible();
    }

    private ExtJsComponent getToolElement(String id) {
        return new ExtJsComponent(this).setClasses("x-tool-" + id);
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
