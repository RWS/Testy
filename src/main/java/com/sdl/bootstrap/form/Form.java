package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated package "com.sdl.bootstrap.*" is deprecated, please use new package "com.sdl.selenium.bootstrap.*"
 */
public class Form extends com.sdl.selenium.bootstrap.form.Form {
    public Form() {
    }

    public Form(WebLocator container) {
        super(container);
    }

    public Form(WebLocator container, String title) {
        super(container, title);
    }
}