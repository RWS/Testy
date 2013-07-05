package com.extjs.selenium.button;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.button.SelectFiles;
import org.apache.log4j.Logger;

public class DownloadButton extends SelectFiles {

    private static final Logger logger = Logger.getLogger(DownloadButton.class);

    public DownloadButton() {
        setClassName("DownloadButton");
    }

    public DownloadButton(WebLocator container) {
        this();
        setElPath(new Button(container).getPath());
    }

    public DownloadButton(WebLocator container, String text) {
        this();
        setElPath(new Button(container, text).getPath());
    }
}
