package com.sdl.selenium.web.test;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class TextField extends WebLocator {
    private static final Logger LOGGER = Logger.getLogger(TextField.class);

    private PathBuilder2 pathBuilder = new PathBuilder2();

    public TextField(By... by) {
        pathBuilder = new PathBuilder2(this, by){
            @Override
            protected String getItemPathText() {
                String selector = hasText() ? super.getItemPathText() : "";
                if (hasIconCls()) {
                    selector += (selector.length() > 0 ? " and " : "") + "count(.//*[contains(@class, '" + getIcon() + "')]) > 0";
                }
                return selector.length() == 0 ? null : selector;
            }
        };
        setPathBuilder(pathBuilder);
    }

    public TextField(WebLocator container, By... by) {
        this(by);
        pathBuilder.setContainer(container);
    }

    private String icon;

    public String getIcon() {
        return icon;
    }

    public <T extends TextField> T setIcon(final String icon) {
        this.icon = icon;
        pathBuilder.setIcon(icon);
        return (T) this;
    }

    public Boolean hasIconCls() {
        return icon != null && !icon.equals("");
    }
}
