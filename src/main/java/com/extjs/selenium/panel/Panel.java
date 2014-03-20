package com.extjs.selenium.panel;

import com.extjs.selenium.ExtJsComponent;
import com.extjs.selenium.Utils;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class Panel extends ExtJsComponent {
    private static final Logger logger = Logger.getLogger(Panel.class);

    private String headerBaseCls;

    public Panel() {
        setClassName("Panel");
        setBaseCls("x-panel");
        setHeaderBaseCls(getBaseCls());
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

    /**
     * exclude ids that start-with ext-gen (Ext auto generated ids)
     *
     * @return string
     */
    protected String getDefaultExcludePath() {
        return " and not(contains(@class, 'x-hide-display')) " +
                "and not(contains(@class, 'x-masked'))";
    }

    protected String getHeaderSelector() {
        String selector = "";
        if (hasTitle()) {
            selector = " and count(*[contains(@class,'" + getHeaderBaseCls() + "-header') or contains(@class, '-tl')]//*[text()='" + getTitle() + "']) > 0";
        }
        return selector;
    }

    public String getItemPath(boolean disabled) {
        String selector = getBasePathSelector();
        selector += getHeaderSelector();

        // limit results to panel only
        selector += getDefaultExcludePath();

        selector = Utils.fixPathSelector(selector);
        return "//*[" + selector + "]";
    }

    /**
     * return only body path but no panel path
     *
     * @return string
     */
    public String getBodyPath() {
        return "//*[contains(@class, '" + getBaseCls() + "-body')]";
    }

    public ExtJsComponent getBodyComponent() {
        return new ExtJsComponent(this, getBodyPath());
    }

    public boolean clickOnTool(String id) {
        WebLocator toolElement = new WebLocator(this).setClasses("x-tool-" + id).setVisibility(true);
        toolElement.setInfoMessage("x-tool-" + id);
        return toolElement.click();
    }

    public boolean close() {
        boolean closed = clickOnTool("close");
        if(closed){
            Utils.sleep(50);
        }
        return closed;
    }

    public boolean maximize() {
        boolean isMaximized = isMaximized();
        boolean maximized = isMaximized || clickOnTool("maximize");
        if(!isMaximized && maximized){
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
