package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICombo;

public class MultiSelect extends WebLocator implements ICombo {

    public MultiSelect() {
        setClassName("SelectPicker");
        setBaseCls("btn dropdown-toggle");
        setTag("button");
    }

    public MultiSelect(WebLocator container) {
        this();
        setContainer(container);
    }

    public MultiSelect(WebLocator container, String label) {
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