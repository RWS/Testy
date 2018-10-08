package com.sdl.selenium.extjs6.form;

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

    public FieldSet(WebLocator container, String text) {
        this(container);
        setText(text);
    }

    public boolean isCollapsed() {
        String cls = getAttributeClass();
        return cls != null && cls.contains("x-fieldset-collapsed");
    }

    public boolean expand() {
        WebLocator legendElement = new WebLocator(this).setText(getPathBuilder().getText());
        return !isCollapsed() || legendElement.click();
    }

    public boolean collapse() {
        WebLocator legendElement = new WebLocator(this).setText(getPathBuilder().getText());
        return isCollapsed() || legendElement.click();
    }
}