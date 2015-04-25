package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.By;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RadioGroup extends ExtJsComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(RadioGroup.class);

    private Radio radio = new Radio(By.container(this));

    public RadioGroup(By...bys) {
        getPathBuilder().defaults(By.baseCls("x-form-radio-group")).init(bys);
    }

    public RadioGroup(WebLocator container) {
        this(By.container(container));
    }

    public RadioGroup(WebLocator container, String name) {
        this(By.container(container));
        radio.getPathBuilder().setName(name);
    }

    public boolean selectByLabel(String label) {
        return selectByLabel(label, SearchType.EQUALS);
    }

    public boolean selectByLabel(String label, SearchType searchType) {
        radio.getPathBuilder().setLabel(label, searchType);
        boolean selected = !isDisabled() && radio.click();
        radio.getPathBuilder().setLabel(null);
        return selected;
    }

    public boolean selectByValue(String value) {
        radio.getPathBuilder().setText(value);
        boolean selected = !isDisabled() && radio.click();
        radio.getPathBuilder().setText(null);
        return selected;
    }

    public String getLabelName(String label) {
        WebLocator locator = new WebLocator(By.container(radio), By.xpath("/following-sibling::label[contains(text(),'" + label + "')]"));
        return locator.getHtmlText();
    }

    public boolean isDisabled() {
        WebLocator locator = new WebLocator(By.xpath(radio.getPathBuilder().getPath(true)));
        return locator.exists();
    }
}