package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RadioGroup extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(RadioGroup.class);

    private Radio radio = new Radio(this);

    public RadioGroup() {
        setClassName("RadioGroup");
        setAttribute("role", "radiogroup");
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
        boolean selected = !radio.isSelected() || radio.click();
        radio.setLabel(null);
        return selected;
    }

    public String getLabelName(String label) {
        WebLocator locator = new WebLocator(radio).setElxPath("/following-sibling::label[contains(text(),'" + label + "')]");
        return locator.getText();
    }

//    public boolean isDisabled() {
//        WebLocator locator = new WebLocator().setElxPath(radio.getXPath(true));
//        return locator.exists();
//    }
}