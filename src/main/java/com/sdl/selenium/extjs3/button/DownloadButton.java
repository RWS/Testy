package com.sdl.selenium.extjs3.button;

import com.sdl.selenium.bootstrap.button.Download;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadButton extends Button implements Download {
    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadButton.class);

    public DownloadButton() {
        setClassName("DownloadButton");
    }

    public DownloadButton(WebLocator container) {
        this();
        setContainer(container);
    }

    public DownloadButton(WebLocator container, String text) {
        this(container);
        setText(text, SearchType.EQUALS);
    }

    /**
     * if WebDriverConfig.isSilentDownload() is true, se face silentDownload, is is false se face download with AutoIT.
     * Download file with AutoIT, works only on FireFox. SilentDownload works FireFox and Chrome
     * Use only this: button.download("C:\\TestSet.tmx");
     * return true if the downloaded file is the same one that is meant to be downloaded, otherwise returns false.
     *
     * @param fileName e.g. "TestSet.tmx"
     */
    @Override
    public boolean download(String fileName) {
        openBrowse();
        return executor.download(fileName, 10000L);
    }

    private void openBrowse() {
        executor.browse(this);
    }
}
