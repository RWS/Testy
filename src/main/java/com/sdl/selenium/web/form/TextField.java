package com.sdl.selenium.web.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.MultiThreadClipboardUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextField extends WebLocator implements ITextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextField.class);

    public TextField() {
        withClassName("TextField");
        withTag("input");
    }

    public TextField(WebLocator container) {
        this();
        withContainer(container);
    }

    public TextField(String id) {
        this();
        withId(id);
    }
    /**
     * @param value value
     * @param searchTypes accept only SearchType.EQUALS, SearchType.CONTAINS, SearchType.STARTS_WITH, SearchType.TRIM
     * @return current element
     */
    public <T extends ITextField> T setPlaceholder(final String value, SearchType ...searchTypes) {
        withAttribute("placeholder", value, searchTypes);
        return (T) this;
    }

    public boolean pasteInValue(String value) {
        assertReady();
        if (value != null) {
            currentElement.clear();
            MultiThreadClipboardUtils.copyString(value);
            MultiThreadClipboardUtils.pasteString(this);
            LOGGER.info("Set value(" + this + "): " + value + "'");
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