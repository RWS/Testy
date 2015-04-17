package com.sdl.selenium.bootstrap.window;

import com.sdl.selenium.bootstrap.button.Button;
import com.sdl.selenium.conditions.ConditionManager;
import com.sdl.selenium.conditions.ElementRemovedSuccessCondition;
import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebLocator;

public class PopOver extends WebLocator {

    public PopOver(By... bys) {
        getPathBuilder().defaults(By.classes("popover"), By.template("title", "count(.//*[@class='popover-title' and text()='%s'])> 0"), By.infoMessage("PopOver")).init(bys);
    }

    public PopOver(String title) {
        this(By.title(title));
    }

    public PopOver(String title, String message) {
        this(By.title(title), By.template("title", "count(.//*[@class='popover-title' and text()='%s']//following-sibling::*[@class='popover-content' and text()='" + message + "'])> 0"));
    }

    public boolean close() {
        return new Button(By.container(this), By.text("Close")).click() && waitClose(this, 2000);
    }

    private boolean waitClose(WebLocator popOver, long time) {
        return new ConditionManager(time).add(new ElementRemovedSuccessCondition(popOver)).execute().isSuccess();
    }
}