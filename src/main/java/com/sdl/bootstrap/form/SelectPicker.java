package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class SelectPicker extends WebLocator {
    private static final Logger logger = Logger.getLogger(SelectPicker.class);

    public SelectPicker() {
        setClassName("SelectPicker");
        setBaseCls("btn dropdown-toggle");
        setTag("button");
    }

    public SelectPicker(WebLocator container) {
        this();
        setContainer(container);
    }

    public SelectPicker(WebLocator container, String label) {
        this(container);
        setLabel(label);
    }

    public boolean select(String value) {
        if (click()) {
            WebLocator select = new WebLocator(this, "//following-sibling::*[contains(@class, 'dropdown-menu')]//span[text()='" + value + "']").setInfoMessage("SelectPicker");
            return select.click();
        }
        return false;
    }

    public String getValue() {
        return getHtmlText().trim();
    }
}