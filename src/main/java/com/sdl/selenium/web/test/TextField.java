package com.sdl.selenium.web.test;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.PathBuilder;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class TextField extends WebLocator {
    private static final Logger LOGGER = Logger.getLogger(TextField.class);

//    private IconPathBuilder iconPathBuilder = new IconPathBuilder();

    public TextField(By... by) {
//        iconPathBuilder = new IconPathBuilder(by);
        setPathBuilder(new PathBuilder(by));
    }

    public TextField(WebLocator container, By... by) {
        this(by);
        getPathBuilder().setContainer(container);
    }

    private String icon;

    public String icon() {
        return icon;
    }

    public <T extends TextField> T icon(final String icon) {
        this.icon = icon;
//        iconPathBuilder.icon(icon);

        String key = "icon-cls";
        getPathBuilder().setElPathSuffix(key, getPathBuilder().applyTemplate(key, icon));
        return (T) this;
    }
}
