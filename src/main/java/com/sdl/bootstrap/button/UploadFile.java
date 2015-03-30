package com.sdl.bootstrap.button;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated package "com.sdl.bootstrap.*" is deprecated, please use new package "com.sdl.selenium.bootstrap.*"
 */
public class UploadFile extends com.sdl.selenium.bootstrap.button.UploadFile {
    public UploadFile() {
    }

    public UploadFile(WebLocator container) {
        super(container);
    }

    public UploadFile(WebLocator container, String label) {
        super(container, label);
    }
}