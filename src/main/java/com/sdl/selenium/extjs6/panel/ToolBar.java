package com.sdl.selenium.extjs6.panel;

import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToolBar extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToolBar.class);

    public ToolBar() {
        setClassName("ToolBar");
        setBaseCls("x-toolbar");
    }

    public ToolBar(WebLocator container) {
        this();
        setContainer(container);
    }
}
