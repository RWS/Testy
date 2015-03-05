package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
/**
 * @deprecated package "com.sdl.bootstrap.*" is deprecated, please use new package "com.sdl.selenium.bootstrap.*"
 */
public class TextField extends com.sdl.selenium.bootstrap.form.TextField {
    public TextField() {
    }

    public TextField(WebLocator container) {
        super(container);
    }

    public TextField(WebLocator container, String label) {
        super(container, label);
    }
}