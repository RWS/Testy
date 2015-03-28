package com.sdl.bootstrap.button;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated package "com.sdl.bootstrap.*" is deprecated, please use new package "com.sdl.selenium.bootstrap.*"
 */
public class DownloadFile extends com.sdl.selenium.bootstrap.button.DownloadFile {

    public DownloadFile() {
    }

    public DownloadFile(WebLocator container) {
        super(container);
    }

    public DownloadFile(WebLocator container, String label) {
        super(container, label);
    }
}