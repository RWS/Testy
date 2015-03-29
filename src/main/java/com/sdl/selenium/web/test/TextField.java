package com.sdl.selenium.web.test;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.PathBuilder;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class TextField extends WebLocator {
    private static final Logger LOGGER = Logger.getLogger(TextField.class);

    public TextField(By... bys) {
        setPathBuilder(new PathBuilder(bys));
        getPathBuilder().defaults(By.baseCls("BaseCls"));
        getPathBuilder().setTemplate("icon-cls", "count(.//*[contains(@class, '%s')]) > 0");
    }

    public TextField(WebLocator container, By... bys) {
        this(bys);
        getPathBuilder().setContainer(container);
    }

    private String icon;

    public String icon() {
        return icon;
    }

    public <T extends TextField> T icon(final String icon) {
        this.icon = icon;
        String key = "icon-cls";
        getPathBuilder().setElPathSuffix(key, getPathBuilder().applyTemplate(key, icon));
        return (T) this;
    }
}
