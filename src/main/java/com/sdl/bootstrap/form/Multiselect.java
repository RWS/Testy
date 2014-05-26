package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICombo;

public class Multiselect extends WebLocator implements ICombo {

    public Multiselect() {
        setClassName("SelectPicker");
        setBaseCls("btn dropdown-toggle");
        setTag("button");
    }

    public Multiselect(WebLocator container) {
        this();
        setContainer(container);
    }

    public Multiselect(WebLocator container, String label) {
        this(container);
        setLabel(label);
    }

    @Override
    public boolean select(String value) {
        if (click()) {
            WebLocator select = new WebLocator(this, "//following-sibling::*[contains(@class, 'dropdown-menu')]//span[text()='" + value + "']")
                    .setInfoMessage("select: '" + value + "'");
            return select.click();
        }
        return false;
    }

    @Override
    public String getValue() {
        return getHtmlText().trim();
    }

    @Override
    public boolean setValue(String value) {
        return select(value);
    }
}