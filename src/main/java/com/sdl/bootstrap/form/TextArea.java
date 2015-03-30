package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated package "com.sdl.bootstrap.*" is deprecated, please use new package "com.sdl.selenium.bootstrap.*"
 */
public class TextArea extends com.sdl.selenium.bootstrap.form.TextArea {
    public TextArea() {
    }

    public TextArea(WebLocator container) {
        super(container);
    }

    public TextArea(WebLocator container, String label) {
        super(container, label);
    }
}