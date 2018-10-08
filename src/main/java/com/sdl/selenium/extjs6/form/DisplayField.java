package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class DisplayField extends WebLocator {

    public DisplayField() {
        setClassName("DisplayField");
        setBaseCls("x-form-display-field");
        setTag("div");
    }

    public DisplayField(WebLocator container) {
        this();
        setContainer(container);
    }

    public DisplayField(WebLocator container, String label) {
        this(container);
        setLabel(label, SearchType.DEEP_CHILD_NODE_OR_SELF);
    }
}