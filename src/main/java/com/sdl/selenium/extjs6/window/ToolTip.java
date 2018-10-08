package com.sdl.selenium.extjs6.window;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class ToolTip extends WebLocator {

    public ToolTip() {
        setInfoMessage("ToolTip");
        setClasses("x-tip");
        setAttribute("aria-hidden", "false");
    }

    public ToolTip(String message, SearchType... searchTypes) {
        this();
        WebLocator messageEl = new WebLocator(this).setClasses("x-tip-body").setText(message, searchTypes);
        setChildNodes(messageEl);
    }

    public String getMessage() {
        WebLocator messageEl = new WebLocator(this).setClasses("x-tip-body");
        return messageEl.getText();
    }
}