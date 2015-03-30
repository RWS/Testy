package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
/**
 * @deprecated package "com.sdl.bootstrap.*" is deprecated, please use new package "com.sdl.selenium.bootstrap.*"
 */
public class UneditableInput extends com.sdl.selenium.bootstrap.form.UneditableInput {
    public UneditableInput() {
    }

    public UneditableInput(WebLocator container) {
        super(container);
    }

    public UneditableInput(WebLocator container, String label) {
        super(container, label);
    }
}