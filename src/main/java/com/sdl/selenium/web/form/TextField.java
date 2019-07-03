package com.sdl.selenium.web.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.MultiThreadClipboardUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TextField extends WebLocator implements ITextField {

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
    /**
     * @param value value
     * @param searchTypes accept only SearchType.EQUALS, SearchType.CONTAINS, SearchType.STARTS_WITH, SearchType.TRIM
     * @return current element
     */
    public <T extends ITextField> T setPlaceholder(String value, SearchType ...searchTypes) {
        setAttribute("placeholder", value, searchTypes);
        return (T) this;
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