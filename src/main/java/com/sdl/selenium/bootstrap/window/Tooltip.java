package com.sdl.selenium.bootstrap.window;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebLocator;

public class Tooltip extends WebLocator {

    public Tooltip(By ...bys) {
        getPathBuilder().defaults(By.classes("tooltip"), By.template("title", "count(//*[@class='tooltip-inner' and text()='%s']) > 0"), By.infoMessage("Tooltip")).init(bys);
    }

    public Tooltip(String message) {
        this(By.title(message), By.infoMessage("Tooltip has message: '" + message + "'."));
    }
}