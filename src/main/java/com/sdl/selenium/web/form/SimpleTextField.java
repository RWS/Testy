package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated class "SimpleTextField*" is deprecated, please use new package "TextField"
 */
public class SimpleTextField extends TextField {
    public SimpleTextField() {
    }

    public SimpleTextField(WebLocator container) {
        super(container);
    }

    public SimpleTextField(String id) {
        super(id);
    }
}