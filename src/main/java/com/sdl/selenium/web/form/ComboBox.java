package com.sdl.selenium.web.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;

public class ComboBox extends WebLocator implements ICombo {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComboBox.class);

    public ComboBox() {
        withClassName("ComboBox");
        withTag("select");
    }

    public ComboBox(WebLocator container) {
        this();
        withContainer(container);
    }

    @Override
    public boolean select(String value) {
        boolean selected = waitToRender();
        assertThat("Element was not rendered " + toString(), selected);
        selected = doSelect(value);
        assertThat("Could not selected value on : " + this, selected);
        return selected;
    }

    public boolean doSelect(String value) {
        boolean selected;
        ready();
        if ("".equals(value)) {
            LOGGER.warn("value is empty");
            selected = false;
        } else {
            new Select(getWebElement()).selectByVisibleText(value);
            selected = true;
        }
        if (selected) {
            LOGGER.info("Set value(" + this + "): " + value);
        }
        return selected;
    }

    @Override
    public String getValue() {
        String value = this.getAttribute("value");
        WebLocator el = new WebLocator(this).withTag("option").withAttribute("value", value, SearchType.CONTAINS);
        return el.getText();
    }
}
