package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RadioGroup extends ExtJsComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(RadioGroup.class);

    private Radio radio = new Radio(this);

    public RadioGroup() {
        setClassName("RadioGroup");
        setBaseCls("x-form-radio-group");
    }

    public RadioGroup(WebLocator container) {
        this();
        setContainer(container);
    }

    public RadioGroup(WebLocator container, String name) {
        this(container);
        radio.setName(name);
    }

    public boolean selectByLabel(String label) {
        return selectByLabel(label, SearchType.EQUALS);
    }

    public boolean selectByLabel(String label, SearchType searchType) {
        radio.setLabel(label, searchType);
        boolean selected = !isDisabled() && radio.click();
        radio.setLabel(null);
        return selected;
    }

    public boolean selectByValue(String value) {
        radio.setText(value);
        boolean selected = !isDisabled() && radio.click();
        radio.setText(null);
        return selected;
    }

    public String getLabelName(String label) {
        WebLocator locator = new WebLocator(radio).setElPath("/following-sibling::label[contains(text(),'" + label + "')]");
        return locator.getText();
    }

    public boolean isDisabled() {
        WebLocator locator = new WebLocator().setElPath(radio.getXPath(true));
        return locator.ready();
    }

    public boolean isEnabled() {
        WebLocator locator = new WebLocator().setElPath(radio.getXPath(false));
        return locator.ready();
    }
}