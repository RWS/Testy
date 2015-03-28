package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated package "com.sdl.bootstrap.*" is deprecated, please use new package "com.sdl.selenium.bootstrap.*"
 */
public class InputAppend extends com.sdl.selenium.bootstrap.form.InputAppend {
    public InputAppend() {
    }

    public InputAppend(WebLocator container) {
        super(container);
    }

    public InputAppend(WebLocator container, String label) {
        super(container, label);
    }
}