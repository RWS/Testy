package com.sdl.selenium.web.table;

import com.sdl.selenium.web.Locator;

public abstract class AbstractCell extends Locator implements ICell {

    @Override
    public String getText() {
        return executor().getText(this);
    }

    @Override
    public boolean clickAt() {
        return executor().clickAt(this);
    }

    @Override
    public boolean doClickAt() {
        return executor().clickAt(this);
    }

    @Override
    public boolean click() {
        return executor().click(this);
    }

    @Override
    public boolean doClick() {
        return executor().click(this);
    }

    @Override
    public boolean doubleClickAt() {
        return executor().doubleClickAt(this);
    }

    @Override
    public boolean doDoubleClickAt() {
        return executor().doubleClickAt(this);
    }

    public boolean sendKeys(CharSequence... charSequences) {
        return executor().sendKeys(this, charSequences);
    }
}
