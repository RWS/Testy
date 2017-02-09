package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckBox extends WebLocator implements ICheck {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckBox.class);

    public CheckBox() {
        setClassName("CheckBox");
        setBaseCls("x-form-checkbox");
    }

    public CheckBox(WebLocator container) {
        this();
        setContainer(container);
    }

    public CheckBox(WebLocator container, String label) {
        this(container);
        setLabel(label, SearchType.CONTAINS);
    }

    public CheckBox(String boxLabel, WebLocator container) {
        this(container);
        setLabel(boxLabel);
        setLabelPosition("/../");
    }

    @Override
    public boolean isSelected() {
        WebLocator el = new WebLocator(this).setElxPath("/../input");
        String select = el.getAttribute("aria-checked");
        return select != null && select.contains("true");
    }

    @Override
    public boolean isDisabled() {
        String cls = getAttributeClass();
        return (cls != null && cls.contains("disabled")) || getAttribute("disabled") != null;
    }
}