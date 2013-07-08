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
        setContainer(container);
    }

    public DownloadButton(WebLocator container, String text) {
        this(container);
        setText(text);
        setSearchTextType("eq");
    }
}
