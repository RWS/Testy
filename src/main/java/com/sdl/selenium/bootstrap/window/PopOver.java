package com.sdl.selenium.bootstrap.window;

import com.sdl.selenium.bootstrap.button.Button;
import com.sdl.selenium.conditions.ConditionManager;
import com.sdl.selenium.conditions.ElementRemovedSuccessCondition;
import com.sdl.selenium.web.WebLocator;

import java.time.Duration;

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
        return new Button(this, "Close").click() && waitClose(this, Duration.ofSeconds(2));
    }

    private boolean waitClose(WebLocator popOver, Duration duration) {
        return new ConditionManager(duration).add(new ElementRemovedSuccessCondition(popOver)).execute().isSuccess();
    }
}