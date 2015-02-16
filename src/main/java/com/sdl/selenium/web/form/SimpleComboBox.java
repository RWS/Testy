package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.support.ui.Select;

public class SimpleComboBox extends WebLocator implements ICombo {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleComboBox.class);

    public SimpleComboBox() {
        setClassName("SimpleComboBox");
        setTag("select");
    }

    public SimpleComboBox(WebLocator container) {
        this();
        setContainer(container);
    }

    @Override
    public boolean select(String value) {
        boolean selected = false;
        if (ready()) {
            if ("".equals(value)) {
                LOGGER.warn("value is empty");
                selected = true;
            } else {
                new Select(currentElement).selectByVisibleText(value);
                selected = true;
            }
        }
        if (selected) {
            LOGGER.info("Set value(" + this + "): " + value);
        }
        return selected;
    }

    @Override
    public boolean setValue(String value) {
        return select(value);
    }

    @Override
    public String getValue() {
        String value = this.getAttribute("value");
        return new WebLocator(this, "//option[contains(@value, '" + value + "')]").getHtmlText();
    }
}
