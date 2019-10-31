package com.sdl.selenium.web.form;

import com.google.common.base.Strings;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class ComboBox extends Field implements ICombo {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ComboBox.class);

    public ComboBox() {
        setClassName("ComboBox");
        setTag("select");
    }

    public ComboBox(WebLocator container) {
        this();
        setContainer(container);
    }

    public boolean selectByIndex(int index) {
        return doSelect(index);
    }

    @Override
    public boolean select(String value) {
        boolean selected = waitToRender();
        assertThat("Element was not rendered " + toString(), selected);
        selected = doSelect(value);
        assertThat("Could not selected value on : " + this, selected);
        return selected;
    }

    private boolean doSelect(int index) {
        ready();
        new Select(getWebElement()).selectByIndex(index);
        log.info("Set value(" + this + "): " + index);
        return true;
    }

    public boolean doSelect(String value) {
        boolean selected;
        ready();
        if (Strings.isNullOrEmpty(value)) {
            log.warn("value is empty");
            selected = false;
        } else {
            new Select(getWebElement()).selectByVisibleText(value);
            selected = true;
        }
        if (selected) {
            log.info("Set value(" + this + "): " + value);
        }
        return selected;
    }

    @Override
    public String getValue() {
        String value = this.getAttribute("value");
        WebLocator el = new WebLocator(this).setTag("option").setAttribute("value", value, SearchType.CONTAINS);
        return el.getText();
    }

    @Override
    public List<String> getAllValues() {
        return null;
    }

    @Override
    public boolean expand() {
        return false;
    }
}