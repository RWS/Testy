package com.sdl.selenium.bootstrap.button;

import com.sdl.selenium.web.WebLocator;

/**
 * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
 * <p>Example:</p>
 * <pre>{@code
 * <div class="controls">
 *      <button class="btn download-btn" type="button"><i class="icon-download-alt"></i> Download</button>
 * </div>
 * }</pre>
 * <p>In Java write this:</p>
 * <pre>{@code
 * DownloadFile downloadFile = new DownloadFile().withText(" Download");
 * downloadFile.download("text.docx")
 * }</pre>
 */
public class DownloadFile extends WebLocator implements Download {

    public DownloadFile() {
        withClassName("DownloadFile");
        withBaseCls("btn");
        withTag("button");
    }

    /**
     * @param container parent
     */
    public DownloadFile(WebLocator container) {
        this();
        withContainer(container);
    }

    public DownloadFile(WebLocator container, String label) {
        this(container);
        withLabel(label);
    }

    public DownloadFile(WebLocator container, String label, boolean isInternationalized) {
        this(container);
        withLabel(label, isInternationalized);
    }

    /**
     * if WebDriverConfig.isSilentDownload() is true, se face silentDownload, is is false se face download with AutoIT.
     * Download file with AutoIT, works only on FireFox. SilentDownload works FireFox and Chrome
     * Use only this: button.download("C:\\TestSet.tmx");
     * @return true if the downloaded file is the same one that is meant to be downloaded, otherwise returns false.
     *
     * @param fileName e.g. "TestSet.tmx"
     */
    @Override
    public boolean download(String fileName) {
        return download(fileName, 10000L);
    }

    public boolean download(String fileName, long timeoutMillis) {
        openBrowse();
        return executor.download(fileName, timeoutMillis);
    }

    public void openBrowse() {
        executor.browse(this);
    }

    public boolean isDisabled(){
        String cls = getAttributeClass();
        return (cls != null && cls.contains("disabled")) || getAttribute("disabled") != null;
    }
}