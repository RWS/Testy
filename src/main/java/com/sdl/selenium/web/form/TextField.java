package com.sdl.selenium.web.form;

import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.utils.MultiThreadClipboardUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TextField extends Locator implements ITextField {

    public TextField() {
        setClassName("TextField");
        setTag("input");
    }

    public TextField(Locator container) {
        this();
        setContainer(container);
    }

    public TextField(String id) {
        this();
        setId(id);
    }

    public boolean pasteInValue(String value) {
        ready();
        if (value != null) {
            getWebElement().clear();
            MultiThreadClipboardUtils.copyString(value);
            MultiThreadClipboardUtils.pasteString(this);
            log.info("Set value(" + this + "): " + value + "'");
            return true;
        }
        return false;
    }

    public boolean setValue(String value) {
        ready();
        return executor().setValue(this, value);
    }

    public String getValue() {
        ready();
        return executor().getValue(this);
    }

    /**
     * @return true is the element doesn't have attribute readonly
     */
    public boolean isEditable() {
        return !"true".equals(getAttribute("readonly"));
    }

    @Override
    public boolean clear() {
        return executor().clear(this);
    }

    @Override
    public boolean doClear() {
        return executor().clear(this);
    }

    @Override
    public void sendKeys(CharSequence... charSequences) {
        executor().sendKeys(this, charSequences);
    }

    @Override
    public boolean doSendKeys(CharSequence... charSequences) {
        return executor().sendKeys(this, charSequences);
    }

    @Override
    public boolean focus() {
        return executor().focus(this);
    }

    @Override
    public boolean doFocus() {
        return executor().clear(this);
    }

    @Override
    public boolean blur() {
        return executor().blur(this);
    }

    @Override
    public boolean doBlur() {
        return executor().blur(this);
    }

    @Override
    public boolean mouseOver() {
        return executor().mouseOver(this);
    }

    @Override
    public boolean doMouseOver() {
        return executor().mouseOver(this);
    }
}