package com.sdl.selenium.extjs3.panel;

import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;

public class Panel extends Locator {

    private String headerBaseCls;

    public Panel() {
        setClassName("Panel");
        setBaseCls("x-panel");
        setHeaderBaseCls(getXPathBuilder().getBaseCls());
        setElPathSuffix("exclude-hide-cls", "not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))");
        setTemplate("title", "count(*[contains(@class,'" + getHeaderBaseCls() + "-header') or contains(@class, '-tl')]//*[text()='%s']) > 0");
    }

    public Panel(String title) {
        this();
        setTitle(title);
    }

    public Panel(Locator container) {
        this();
        setContainer(container);
    }

    public Panel(Locator container, String title) {
        this(container);
        setTitle(title);
    }

    public Panel(String cls, Locator container, String excludeClass) {
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

    public Locator getBodyComponent() {
        return new WebLocator(this).setElPath("//*[contains(@class, '" + getXPathBuilder().getBaseCls() + "-body')]");
    }

    /**
     * click on element with class "x-tool-" + id
     * @param id element
     * @return true | false
     */
    public boolean clickOnTool(String id) {
        WebLocator toolElement = getToolElement(id).setVisibility(true);
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
        WebLocator maximizeTool = getToolElement("maximize");
        return !maximizeTool.isVisible();
    }

    private WebLocator getToolElement(String id) {
        return new WebLocator(this).setClasses("x-tool-" + id);
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
