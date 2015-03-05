package com.sdl.bootstrap.button;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated package "com.sdl.bootstrap.*" is deprecated, please use new package "com.sdl.selenium.bootstrap.*"
 */
public class Button extends com.sdl.selenium.bootstrap.button.Button {
    public Button() {
    }

    public Button(WebLocator container) {
        super(container);
    }

    public Button(WebLocator container, String text) {
        super(container, text);
    }
}