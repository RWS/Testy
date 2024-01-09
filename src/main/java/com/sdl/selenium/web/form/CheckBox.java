package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.WebElement;

public class CheckBox extends WebLocator implements ICheck {

    public CheckBox() {
        setClassName("CheckBox");
        setTag("input");
        setType("checkbox");
    }

    public CheckBox(WebLocator container) {
        this();
        setContainer(container);
    }

    public CheckBox(WebElement webElement) {
        this();
        setWebElement(webElement);
    }

    public CheckBox(String id) {
        this();
        setId(id);
    }

    @Deprecated
    public boolean isSelected() {
        return isChecked();
    }

    @Override
    public boolean isChecked() {
        return ready() && executor.isSelected(this);
    }
}