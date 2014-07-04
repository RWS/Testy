package com.sdl.bootstrap.window;

import com.sdl.selenium.web.WebLocator;

public class PopOver extends WebLocator {

    private PopOver() {
        setInfoMessage("PopOver");
        setClasses("popover");
        setTemplate("title", "count(.//*[@class='popover-title' and text()='%s'])> 0");
    }

    public PopOver(String title, String message) {
        this(title);
        setTemplate("title", "count(.//*[@class='popover-title' and text()='%s']//following-sibling::*[@class='popover-content' and text()='" + message + "'])> 0");
    }

    public PopOver(String title) {
        this();
        setTitle(title);
    }
}