package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class SelectPicker extends WebLocator {
    private static final Logger logger = Logger.getLogger(SelectPicker.class);

    private WebLocator button = new WebLocator(this, "//button[contains(@class, 'btn dropdown-toggle')]");

    public SelectPicker() {
        setClassName("SelectPicker");
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
        if (button.click()) {
            WebLocator select = new WebLocator(this, "[contains(@class, 'bootstrap-select')]//*[contains(@class, 'dropdown-menu')]//span[text()='" + value + "']");
            return select.click();
        }
        return false;
    }

    public String getValue() {
        return button.getHtmlText().trim();
    }
}