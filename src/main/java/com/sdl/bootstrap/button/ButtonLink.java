package com.sdl.bootstrap.button;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated package "com.sdl.bootstrap.*" is deprecated, please use new package "com.sdl.selenium.bootstrap.*"
 */
public class ButtonLink extends com.sdl.selenium.bootstrap.button.ButtonLink {

    public ButtonLink() {
    }

    public ButtonLink(WebLocator container) {
        super(container);
    }

    public ButtonLink(WebLocator container, String text) {
        super(container, text);
    }
}