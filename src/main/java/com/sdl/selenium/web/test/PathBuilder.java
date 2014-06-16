package com.sdl.selenium.web.test;

public class PathBuilder extends WebLocatorAbstractBuilderDeprecated {

    public PathBuilder() {

    }

    public PathBuilder(By ...bys) {
        for (By by : bys) {
            by.init(this);
        }
    }
}
