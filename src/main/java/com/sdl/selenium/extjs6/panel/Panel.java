package com.sdl.selenium.extjs6.panel;

import com.sdl.selenium.extjs4.window.XTool;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class Panel extends WebLocator implements XTool {

    public Panel() {
        setClassName("Panel");
        setBaseCls("x-panel");
        WebLocator panelHeader = new WebLocator().setClasses("x-panel-header").setRoot("/");
        WebLocator header = new WebLocator(panelHeader).setClasses("x-title");
        setTemplateTitle(new WebLocator(header));
    }

    public Panel(WebLocator container) {
        this();
        setContainer(container);
    }

    public Panel(WebLocator container, String title, SearchType... searchTypes) {
        this(container);
        if (searchTypes.length == 0) {
            searchTypes = new SearchType[]{SearchType.EQUALS};
        }
        setTitle(title, searchTypes);
    }

    @Override
    public WebLocator getView() {
        return this;
    }

    public WebLocator getToolEl(String type) {
        return new WebLocator(this).setCls("x-tool-" + type).setTemplate("cls", "contains(@class,'%s')");
    }

    public boolean collapse() {
        return !getToolEl("collapse").isPresent() || XTool.super.collapse();
    }

    public boolean expand() {
        return !getToolEl("expand").isPresent() || XTool.super.expand();
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
