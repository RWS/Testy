package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;

public class FieldSet extends WebLocator {

    public FieldSet() {
        setClassName("FieldSet");
        setBaseCls("x-fieldset");
        setTag("fieldset");
        setExcludeClasses("x-hide-display", "x-masked");
        setTemplate("text", "count(.//*[normalize-space(.)=%s]) > 0");
    }

    public FieldSet(Locator container) {
        this();
        setContainer(container);
    }

    public FieldSet(Locator container, String text) {
        this(container);
        setText(text);
    }

    public FieldSet(Locator container, String cls, String text) {
        this(container, text);
        setClasses(cls);
    }

    // methods
    public boolean isCollapsed() {
        String cls = getAttribute("class");
        return cls != null && cls.contains("x-panel-collapsed");
    }

    public boolean expand() {
        WebLocator legendElement = new WebLocator(this).setText(getXPathBuilder().getText());
        boolean expanded = !isCollapsed() || legendElement.click();
        if (expanded) {
            Utils.sleep(500);
        }
        return expanded;
    }
}
