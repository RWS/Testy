package com.sdl.bootstrap.button;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.button.SelectFiles;
import org.apache.log4j.Logger;

public class DownloadFile extends SelectFiles {
    private static final Logger logger = Logger.getLogger(DownloadFile.class);

    public DownloadFile() {
        setClassName("DownloadFile");
        setBaseCls("btn");
        setTag("button");
    }

    /**
     * @param container
     */
    public DownloadFile(WebLocator container) {
        this();
        setContainer(container);
    }

    public DownloadFile(WebLocator container, String label) {
        this(container);
        setLabel(label);
    }
}