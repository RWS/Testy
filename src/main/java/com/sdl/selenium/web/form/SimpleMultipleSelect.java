package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated class "SimpleMultipleSelect*" is deprecated, please use new package "MultipleSelect"
 */
public class SimpleMultipleSelect extends MultipleSelect {
    public SimpleMultipleSelect() {
    }

    public SimpleMultipleSelect(WebLocator container) {
        super(container);
    }

    public SimpleMultipleSelect(WebLocator container, String label) {
        super(container, label);
    }
}
