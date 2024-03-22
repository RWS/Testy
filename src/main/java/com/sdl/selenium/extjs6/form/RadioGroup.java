package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.utils.config.WebLocatorConfig;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class RadioGroup extends WebLocator {

    private String version;

    public RadioGroup() {
        setClassName("RadioGroup");
        setBaseCls("x-form-checkboxgroup");
    }

    public RadioGroup(WebLocator container) {
        this();
        setContainer(container);
    }

    public RadioGroup(WebLocator container, String label, SearchType... searchTypes) {
        this(container);
        WebLocator labelEl = new WebLocator().setTag("label").setText(label, searchTypes);
        setChildNodes(labelEl);
    }

    private String getVersion() {
        return version == null ? WebLocatorConfig.getExtJsVersion() : version;
    }

    public <T extends RadioGroup> T setVersion(String version) {
        this.version = version;
        return (T) this;
    }

    public boolean selectByLabel(String label) {
        return selectByLabel(label, SearchType.EQUALS);
    }

    public boolean selectByLabel(String label, SearchType... searchType) {
        Radio radio = getRadio(label, searchType);
        return radio.isSelected() || radio.click();
    }

    public boolean isSelectedByLabel(String label, SearchType... searchType) {
        Radio radio = getRadio(label, searchType);
        return radio.isSelected();
    }

    public Radio getRadio(String label, SearchType... searchType) {
        WebLocator labelEl = new WebLocator().setTag("label").setText(label, searchType);
        Radio radio = new Radio(this).setTag("*").setBaseCls("x-form-type-radio");
        radio.setChildNodes(labelEl);
        radio.setVersion(getVersion());
        return radio;
    }

    public String getLabelName(String partialLabel) {
        Radio radio = getRadio(partialLabel);
        return radio.getText();
    }

    public boolean isEnabled() {
        String aClass = getAttributeClass();
        return aClass != null && !aClass.contains("x-item-disabled");
    }

    public boolean isDisabled() {
        return !isEnabled();
    }
}