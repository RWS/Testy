package com.sdl.selenium.web.button;

import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class Button extends Locator implements IButton {

    public Button() {
        setClassName("Button");
        setTag("button");
        setTemplate("icon-cls", "count(.//*[contains(concat(' ', @class, ' '), ' %s ')]) > 0");
    }

    public Button(Locator container) {
        this();
        setContainer(container);
    }

    public Button(Locator container, String text, SearchType... searchTypes) {
        this(container);
        setText(text, searchTypes);
    }

    private String iconCls;

    public String getIconCls() {
        return iconCls;
    }

    public <T extends Button> T setIconCls(final String iconCls) {
        this.iconCls = iconCls;
        setTemplateValue("icon-cls", iconCls);
        return (T) this;
    }

    @Override
    public boolean clickAt() {
        return false;
    }

    @Override
    public boolean doClickAt() {
        return false;
    }

    @Override
    public boolean click() {
        return false;
    }

    @Override
    public boolean doClick() {
        return false;
    }

    @Override
    public boolean doubleClickAt() {
        return false;
    }

    @Override
    public boolean doDoubleClickAt() {
        return false;
    }

    @Override
    public WebLocator focus() {
        return null;
    }

    @Override
    public WebLocator doFocus() {
        return null;
    }

    @Override
    public boolean blur() {
        return false;
    }

    @Override
    public boolean doBlur() {
        return false;
    }

    @Override
    public boolean mouseOver() {
        return false;
    }

    @Override
    public boolean doMouseOver() {
        return false;
    }
}
