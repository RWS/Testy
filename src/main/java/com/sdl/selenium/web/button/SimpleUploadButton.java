package com.sdl.selenium.web.button;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated class "SimpleUploadButton*" is deprecated, please use new package "UploadButton"
 */
public class SimpleUploadButton extends UploadButton {
    public SimpleUploadButton() {
    }

    public SimpleUploadButton(WebLocator container) {
        super(container);
    }

    public SimpleUploadButton(WebLocator container, String id) {
        super(container, id);
    }
}
