package com.sdl.selenium.extjs3.panel;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Panel extends ExtJsComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(Panel.class);

    private String headerBaseCls;

    public Panel() {
        withClassName("Panel");
        withBaseCls("x-panel");
        setHeaderBaseCls(getPathBuilder().getBaseCls());
        withElxPathSuffix("exclude-hide-cls", "not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))");
        withTemplate("title", "count(*[contains(@class,'" + getHeaderBaseCls() + "-header') or contains(@class, '-tl')]//*[text()='%s']) > 0");
    }

    public Panel(String title) {
        this();
        withTitle(title);
    }

    public Panel(WebLocator container) {
        this();
        withContainer(container);
    }

    public Panel(WebLocator container, String title) {
        this(container);
        withTitle(title);
    }

    public Panel(String cls, WebLocator container, String excludeClass) {
        this(container);
        withClasses(cls);
        withExcludeClasses(excludeClass);
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
     * @param id element
     * @return true | false
     */
    public boolean clickOnTool(String id) {
        ExtJsComponent toolElement = getToolElement(id).withVisibility(true);
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
        return new ExtJsComponent(this).withClasses("x-tool-" + id);
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
