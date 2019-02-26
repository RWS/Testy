package com.sdl.selenium.extjs6.panel;

import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.WebLocator;

public class ToolBar extends WebLocator {

    public ToolBar() {
        setClassName("ToolBar");
        setBaseCls("x-toolbar");
    }

    public ToolBar(Locator container) {
        this();
        setContainer(container);
    }
}