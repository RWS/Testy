package com.sdl.bootstrap.button;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.button.IButton;
import org.apache.log4j.Logger;

public class Button extends WebLocator implements IButton {
    private static final Logger logger = Logger.getLogger(Button.class);

    public String getIconCls() {
        return iconCls;
    }

    public <T extends Button> T setIconCls(final String iconCls) {
        this.iconCls = iconCls;
        return (T) this;
    }

    private String iconCls;

    public Boolean hasIconCls() {
        return iconCls != null && !iconCls.equals("");
    }

    public Button() {
        setClassName("Button");
        setBaseCls("btn");
        setTag("button");
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

    // Methods

    @Override
    protected String getItemPathText() {
        String selector = hasText() ? super.getItemPathText() : "";
        if (hasIconCls()) {
            selector += " and count(.//*[contains(@class, '" + getIconCls() + "')]) > 0";
        }
        return selector;
    }

    @Override
    public String getItemPath(boolean disabled) {
        String selector = getBasePathSelector();
        selector = Utils.fixPathSelector(selector);
        return "//" + getTag() + ("".equals(selector) ? "" : "[" + selector + "]");
    }

    public boolean isDisabled(){
        return this.getAttribute("disabled") != null;
    }
}