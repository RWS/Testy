package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldSet extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(FieldSet.class);

    public FieldSet(By... bys) {
        getPathBuilder().defaults(
                By.baseCls("x-fieldset"),
                By.tag("fieldset"),
                By.excludeClasses("x-hide-display", "x-masked"),
                By.template("text", "count(.//*[normalize-space(text())='%s']) > 0")
        ).init(bys);
    }

    public FieldSet(WebLocator container) {
        this(By.container(container));
    }

    public FieldSet(WebLocator container, String text) {
        this(By.container(container), By.text(text));
    }

    public FieldSet(WebLocator container, String cls, String text) {
        this(By.container(container),By.classes(cls), By.text(text));
    }

    // methods
    public boolean isCollapsed() {
        String cls = getAttribute("class");
        return cls != null && cls.contains("x-panel-collapsed");
    }

    public boolean expand() {
        WebLocator legendElement = new WebLocator(By.container(this), By.text(getPathBuilder().getText()));
        boolean expanded = !isCollapsed() || legendElement.click();
        if (expanded) {
            Utils.sleep(500);
        }
        return expanded;
    }
}
