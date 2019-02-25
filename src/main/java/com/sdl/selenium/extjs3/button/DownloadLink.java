package com.sdl.selenium.extjs3.button;

import com.sdl.selenium.bootstrap.button.Download;
import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.SearchType;

public class DownloadLink extends Locator implements Download {

    public DownloadLink() {
        setClassName("DownloadLink");
        setTag("a");
    }

    public DownloadLink(Locator container) {
        this();
        setContainer(container);
    }

    public DownloadLink(Locator container, String text) {
        this(container);
        setText(text, SearchType.EQUALS);
    }

//    /**
//     * Wait for the element to be activated when there is deactivation mask on top of it
//     *
//     * @param seconds time
//     */
//    @Override
//    public boolean waitToActivate(int seconds) {
//        return getXPath().contains("ext-ux-livegrid") || super.waitToActivate(seconds);
//    }

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
        return executor().download(fileName, 10000L);
    }

    private void openBrowse() {
        executor().browse(this);
    }
}
