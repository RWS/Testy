package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated package "com.sdl.bootstrap.*" is deprecated, please use new package "com.sdl.selenium.bootstrap.*"
 */
public class CheckBox extends com.sdl.selenium.bootstrap.form.CheckBox {
    public CheckBox() {
    }

    public CheckBox(WebLocator container) {
        super(container);
    }

    public CheckBox(WebLocator container, String label) {
        super(container, label);
    }

    public CheckBox(String boxLabel, WebLocator container) {
        super(boxLabel, container);
    }
}