package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.ui.Select;
import sun.util.logging.resources.logging;

public class SimpleComboBox extends WebLocator implements ICombo {
    private static final Logger logger = Logger.getLogger(SimpleComboBox.class);

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
            if (WebDriverConfig.hasWebDriver()) {
                if ("".equals(value)) {
                    logger.warn("value is empty");
                    selected = true;
                } else {
                    new Select(currentElement).selectByVisibleText(value);
                    selected = true;
                }
            } else {
                selenium.select(getPath(), value);
                selected = true;
            }
        }
        if (selected) {
            logger.info("Set value(" + this + "): " + value);
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
