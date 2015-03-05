package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated package "com.sdl.bootstrap.*" is deprecated, please use new package "com.sdl.selenium.bootstrap.*"
 */
public class MultiSelect extends com.sdl.selenium.bootstrap.form.MultiSelect {

    public MultiSelect() {
    }

    public MultiSelect(WebLocator container) {
        super(container);
    }

    public MultiSelect(WebLocator container, String label) {
        super(container, label);
    }
}