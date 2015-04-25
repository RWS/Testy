package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Radio extends ExtJsComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(Radio.class);

    public Radio(By... bys) {
        getPathBuilder().defaults(
                By.tag("input"),
                By.baseCls("x-form-radio"),
                By.labelPosition("/../"),
                By.template("text", "@value='%s'")
        ).init(bys);
    }

    public Radio(String value) {
        this(By.text(value));
    }

    public Radio(WebLocator container) {
        this(By.container(container));
    }

    public Radio(WebLocator container, String name) {
        this(By.container(container), By.name(name));
    }

    public Radio(String label, WebLocator container) {
        this(By.container(container), By.label(label));
    }

    public boolean isSelected() {
        return isElementPresent() && executor.isSelected(this);
    }
}