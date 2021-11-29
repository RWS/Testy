package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class RadioGroup extends ExtJsComponent {

    private final Radio radio = new Radio(this);

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
        boolean selected = isEnabled() && radio.click();
        radio.setLabel(null);
        return selected;
    }

    public boolean selectByValue(String value) {
        radio.setText(value);
        boolean selected = isEnabled() && radio.click();
        radio.setText(null);
        return selected;
    }

    public String getLabelName(String label) {
        WebLocator locator = new WebLocator(radio).setElPath("/following-sibling::label[contains(text(),'" + label + "')]");
        return locator.getText();
    }

    public boolean isEnabled() {
        return radio.getAttribute("disabled") == null;
    }
}