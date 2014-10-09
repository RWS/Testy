package com.sdl.bootstrap.button;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.button.IButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Button extends WebLocator implements IButton {
    private static final Logger logger = LoggerFactory.getLogger(Button.class);

    private String iconCls;

    public String getIconCls() {
        return iconCls;
    }

    public <T extends Button> T setIconCls(final String iconCls) {
        this.iconCls = iconCls;
        String key = "icon-cls";
        setElPathSuffix(key, applyTemplate(key, iconCls));
        return (T) this;
    }

    public Button() {
        setClassName("Button");
        setBaseCls("btn");
        setTag("button");
        setTemplate("icon-cls", "count(.//*[contains(@class, '%s')]) > 0");
    }

    /**
     * @param container
     */
    public Button(WebLocator container) {
        this();
        setContainer(container);
    }

    public Button(WebLocator container, String text) {
        this(container);
        setText(text);
        setSearchTextType(SearchType.EQUALS);
    }

    public boolean isDisabled() {
        return getAttribute("disabled") != null || getAttributeClass().contains("disabled");
    }
}