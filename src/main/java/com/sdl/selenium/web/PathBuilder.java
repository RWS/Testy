package com.sdl.selenium.web;

public class PathBuilder extends WebLocatorAbstractBuilder {

    public PathBuilder() {

    }

    public PathBuilder(Bys... byses) {
        for (Bys by : byses) {
            by.init(this);
        }
    }
}
