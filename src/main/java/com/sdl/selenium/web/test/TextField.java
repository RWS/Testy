package com.sdl.selenium.web.test;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.XPathBuilder;
import org.apache.log4j.Logger;

public class TextField extends WebLocator {
    private static final Logger LOGGER = Logger.getLogger(TextField.class);

    private XPathBuilder pathBuilder = getPathBuilder();
    public TextField(By... bys) {
        pathBuilder.init(bys);
        pathBuilder.defaults(By.baseCls("BaseCls"));
        pathBuilder.setTemplate("icon-cls", "count(.//*[contains(@class, '%s')]) > 0");
    }

    public TextField(WebLocator container, By... bys) {
        this(bys);
        pathBuilder.setContainer(container);
    }

    private String icon;

    public String icon() {
        return icon;
    }

    public <T extends TextField> T icon(final String icon) {
        this.icon = icon;
        String key = "icon-cls";
        pathBuilder.setElPathSuffix(key, pathBuilder.applyTemplate(key, icon));
        return (T) this;
    }
}
