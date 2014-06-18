package com.sdl.selenium.web.test;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class TextField extends WebLocator {
    private static final Logger LOGGER = Logger.getLogger(TextField.class);

    private IconPathBuilder iconPathBuilder = new IconPathBuilder();

    public TextField(By... by) {
        iconPathBuilder = new IconPathBuilder(this, by);
        setPathBuilder(iconPathBuilder);
    }

    public TextField(WebLocator container, By... by) {
        this(by);
        iconPathBuilder.setContainer(container);
    }

    private String icon;

    public String getIcon() {
        return icon;
    }

    public <T extends TextField> T setIcon(final String icon) {
        this.icon = icon;
        iconPathBuilder.icon(icon);
        return (T) this;
    }
}
