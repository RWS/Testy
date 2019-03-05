package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class FieldSet extends WebLocator {

    public FieldSet() {
        setClassName("FieldSet");
        setBaseCls("x-fieldset");
        setTag("fieldset");
        setExcludeClasses("x-hide-display", "x-masked");
        setTemplate("text", "count(.//*[normalize-space(.)=%s]) > 0");
    }

    public FieldSet(WebLocator container) {
        this();
        setContainer(container);
    }

    public FieldSet(WebLocator container, String text, SearchType... searchTypes) {
        this(container);
        setText(text, searchTypes);
    }

    public boolean isCollapsed() {
        String cls = getAttributeClass();
        return cls != null && cls.contains("x-fieldset-collapsed");
    }

    public boolean expand() {
        return !isCollapsed() || getExpandEl().click();
    }

    public boolean collapse() {
        return isCollapsed() || getExpandEl().click();
    }

    private WebLocator getExpandEl() {
        return new WebLocator(this).setClasses("x-fieldset-header-text").setText(getPathBuilder().getText());
    }
}