package com.sdl.bootstrap.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICheck;
import com.sdl.selenium.web.form.SimpleTextField;
import org.apache.log4j.Logger;

public class CheckBox extends SimpleTextField implements ICheck {
    private static final Logger logger = Logger.getLogger(CheckBox.class);

    public CheckBox() {
        setClassName("CheckBox");
        setType("checkbox");
    }

    public CheckBox(WebLocator container) {
        this();
        setContainer(container);
    }

    public CheckBox(WebLocator container, String label) {
        this(container);
        setLabel(label, SearchType.CONTAINS);
        setLabelPosition("//");
    }

    public CheckBox(String boxLabel, WebLocator container) {
        this(container);
        setLabel(boxLabel);
    }

    public boolean isSelected() {
        return isElementPresent() && executor.isSelected(this);
    }

    public boolean isDisabled(){
        return getAttribute("disabled") != null || getAttributeClass().contains("disabled");
    }
}