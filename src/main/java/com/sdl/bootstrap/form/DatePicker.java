package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated package "com.sdl.bootstrap.*" is deprecated, please use new package "com.sdl.selenium.bootstrap.*"
 */
public class DatePicker extends com.sdl.selenium.bootstrap.form.DatePicker {
    public DatePicker() {
    }

    public DatePicker(WebLocator container) {
        super(container);
    }

    public DatePicker(WebLocator container, String id) {
        super(container, id);
    }
}