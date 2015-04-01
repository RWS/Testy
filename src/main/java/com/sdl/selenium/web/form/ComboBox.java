package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComboBox extends WebLocator implements ICombo {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComboBox.class);

    public ComboBox(By ...bys) {
        getPathBuilder().init(bys);
        getPathBuilder().defaults(By.className("SimpleComboBox"), By.tag("select"));
    }

    public ComboBox(WebLocator container) {
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
        WebLocator el = new WebLocator(this).setElPath("//option[contains(@value, '" + value + "')]");
        return el.getHtmlText();
    }
}
