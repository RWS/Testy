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
        setCls(cls);
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
     * @return
     */
    public String getDefaultExcludePath() {
        return " and not(starts-with(@id, 'ext-gen')) " +
                "and not(contains(@class, '" + getHeaderBaseCls() + "-tc')) " +
                "and not(contains(@class, 'x-hide-display')) " +
                "and not(contains(@class, 'x-masked'))";
    }

    public String getHeaderSelector() {
        String selector = "";
        if (hasTitle()) {
            selector = " and (" +
                    "count(*[contains(@class,'" + getHeaderBaseCls() + "-header')]//*[text()='" + getTitle() + "']) > 0" +
                    " or " +
                    "count(*[contains(@class, '-tl')]//*[contains(@class,'" + getHeaderBaseCls() + "-header')]//*[text()='" + getTitle() + "']) > 0" +
                    ")";
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
     * @return
     */
    public String getBodyPath() {
        return "//*[contains(@class, '" + getBaseCls() + "-body')]";
    }

    public ExtJsComponent getBodyComponent() {
        return new ExtJsComponent(this, getBodyPath());
    }

    public boolean clickOnTool(String id) {
        WebLocator toolElement = new WebLocator(this).setCls("x-tool-" + id).setVisibility(true);
        toolElement.setInfoMessage("x-tool-" + id);
        return toolElement.click();
    }

    public boolean close() {
        boolean closed = clickOnTool("close");
        Utils.sleep(50);
        return closed;
    }

    public boolean maximize() {
        boolean maximized = clickOnTool("maximize");
        Utils.sleep(50);
        return maximized;
    }

    public boolean isMaximized() {
        return !new WebLocator(this, "//*[contains(@class,'x-tool-maximize')]").isVisible();
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
