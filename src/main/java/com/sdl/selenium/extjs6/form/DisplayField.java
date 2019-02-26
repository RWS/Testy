package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.form.TextField;

public class DisplayField extends TextField {

    public DisplayField() {
        setClassName("DisplayField");
        setBaseCls("x-form-display-field");
        setTag("div");
    }

    public DisplayField(Locator container) {
        this();
        setContainer(container);
    }

    public DisplayField(Locator container, String label) {
        this(container);
        setLabel(label, SearchType.DEEP_CHILD_NODE_OR_SELF);
    }
}