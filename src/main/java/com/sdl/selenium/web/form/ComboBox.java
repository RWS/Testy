package com.sdl.selenium.web.form;

import com.google.common.base.Strings;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.internationalization.InternationalizationUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.support.ui.Select;

import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class ComboBox extends WebLocator implements ICombo {

    public ComboBox() {
        setClassName("ComboBox");
        setTag("select");
    }

    public ComboBox(WebLocator container) {
        this();
        setContainer(container);
    }

    @Override
    public boolean select(String value) {
        return select(value, InternationalizationUtils.isInternationalizedTestsSuite());
    }

    public boolean selectByIndex(int index) {
        return doSelect(index);
    }

    public boolean select(String value, boolean isInternationalized) {
        boolean selected = waitToRender();
        assertThat("Element was not rendered " + toString(), selected);
        selected = doSelect(value, isInternationalized);
        assertThat("Could not selected value on : " + this, selected);
        return selected;
    }

    private boolean doSelect(int index) {
        ready();
        new Select(getWebElement()).selectByIndex(index);
        log.info("Set value(" + this + "): " + index);
        return true;
    }

    public boolean doSelect(String value, boolean isInternationalized) {
        value = InternationalizationUtils.getInternationalizedText(value, isInternationalized);
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

    public boolean doSelect(String value) {
        return doSelect(value, InternationalizationUtils.isInternationalizedTestsSuite());
    }

    @Override
    public String getValue() {
        String value = this.getAttribute("value");
        WebLocator el = new WebLocator(this).setTag("option").setAttribute("value", value, SearchType.CONTAINS);
        return el.getText();
    }

    @Override
    public boolean expand() {
        return false;
    }

    @Override
    public boolean collapse() {
        return false;
    }
}