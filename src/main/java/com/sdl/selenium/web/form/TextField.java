package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.MultiThreadClipboardUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TextField extends Field {

    public TextField() {
        setClassName("TextField");
        setTag("input");
    }

    public TextField(WebLocator container) {
        this();
        setContainer(container);
    }

    public TextField(String id) {
        this();
        setId(id);
    }

    public boolean pasteInValue(String value) {
        assertReady();
        if (value != null) {
            getWebElement().clear();
            MultiThreadClipboardUtils.copyString(value);
            MultiThreadClipboardUtils.pasteString(this);
            log.info("Set value(" +  this + "): " + value + "'");
            return true;
        }
        return false;
    }

    public boolean setValue(String value) {
        assertReady();
        return executor.setValue(this, value);
    }

    public String getValue() {
        assertReady();
        return executor.getValue(this);
    }
}