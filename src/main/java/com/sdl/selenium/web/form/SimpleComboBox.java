package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.ui.Select;

public class SimpleComboBox extends WebLocator {
    private static final Logger logger = Logger.getLogger(SimpleComboBox.class);

    public SimpleComboBox() {
        setClassName("SimpleComboBox");
    }

    //TODO
    public SimpleComboBox(String path) {
        this();
        setElPath(path);
    }

    public boolean select(String value) {
        if (isElementPresent()) {
            if(hasWebDriver()){
                new Select(currentElement).selectByVisibleText(value);
                return true;
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
