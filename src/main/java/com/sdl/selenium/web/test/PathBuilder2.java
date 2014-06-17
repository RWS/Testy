package com.sdl.selenium.web.test;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.PathBuilder;
import com.sdl.selenium.web.WebLocator;


public class PathBuilder2 extends PathBuilder {

    public PathBuilder2() {
    }

    public PathBuilder2(WebLocator locator, By... bys) {
        super(locator, bys);
    }

    private String icon;

    public String getIcon() {
        return icon;
    }

    public <T extends PathBuilder2> T setIcon(final String icon) {
        this.icon = icon;
        return (T) this;
    }
}
