package com.sdl.selenium.web.button;

import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class Button extends Locator implements IButton {

    public Button() {
        setClassName("Button");
        setTag("button");
    }

    public Button(WebLocator container) {
        this();
        setContainer(container);
    }

    public Button(WebLocator container, String text, final SearchType... searchTypes) {
        this(container);
        setText(text, searchTypes);
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
