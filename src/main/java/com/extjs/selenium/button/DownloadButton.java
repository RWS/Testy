package com.extjs.selenium.button;

import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @deprecated package "com.extjs.selenium.*" is deprecated, please use new package "com.sdl.selenium.extjs3.*"
 */
public class DownloadButton extends com.sdl.selenium.extjs3.button.DownloadButton {
    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadButton.class);

    public DownloadButton() {
        super();
    }

    public DownloadButton(WebLocator container) {
        super(container);
    }

    public DownloadButton(WebLocator container, String text) {
        super(container, text);
    }
}
