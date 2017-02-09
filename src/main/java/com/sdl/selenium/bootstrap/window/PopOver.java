package com.sdl.selenium.bootstrap.window;

import com.sdl.selenium.bootstrap.button.Button;
import com.sdl.selenium.conditions.ConditionManager;
import com.sdl.selenium.conditions.ElementRemovedSuccessCondition;
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

    public boolean close() {
        return new Button(this, "Close").click() && waitClose(this, 2000);
    }

    private boolean waitClose(WebLocator popOver, long time) {
        return new ConditionManager(time).add(new ElementRemovedSuccessCondition(popOver)).execute().isSuccess();
    }
}