package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated package "com.sdl.bootstrap.*" is deprecated, please use new package "com.sdl.selenium.bootstrap.*"
 */
public class SelectPicker extends com.sdl.selenium.bootstrap.form.SelectPicker {

    public SelectPicker() {
    }

    public SelectPicker(WebLocator container) {
        super(container);
    }

    public SelectPicker(WebLocator container, String label) {
        super(container, label);
    }
}