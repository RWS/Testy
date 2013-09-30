package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.ui.Select;

public class SimpleComboBox extends WebLocator {
    private static final Logger logger = Logger.getLogger(SimpleComboBox.class);

    public SimpleComboBox() {
        setClassName("SimpleComboBox");
        setTag("select");
    }

    public SimpleComboBox(WebLocator container) {
        this();
        setContainer(container);
    }

    public boolean select(String value) {
        if (ready()) {
            if (hasWebDriver()) {
                if ("".equals(value)) {
                    return true;
                } else {
                    new Select(currentElement).selectByVisibleText(value);
                    return true;
                }
            } else {
                selenium.select(getPath(), value);
                return true;
            }
        }
        return false;
    }

    public String getValue() {
        String value = this.getAttribute("value");
        return new WebLocator(this, "//option[contains(@value, '" + value + "')]").getHtmlText();
    }
}
