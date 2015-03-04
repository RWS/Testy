package com.extjs.selenium.button;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated package "com.extjs.selenium.*" is deprecated, please use new package "com.sdl.selenium.extjs3.*"
 */
public class Button extends com.sdl.selenium.extjs3.button.Button {
    public Button() {
        super();
    }

    /**
     * @param container parent
     */
    public Button(WebLocator container) {
        super(container);
    }

    public Button(WebLocator container, String text) {
        super(container, text);
    }
}