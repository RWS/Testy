package com.sdl.selenium.bootstrap.button;

import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.File;

/**
 * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
 * <p>Example:</p>
 * <pre>{@code
 * <div class="controls">
 *      <button class="btn download-btn" type="button"><i class="icon-download-alt"></i> Download</button>
 * </div>
 * }</pre>
 * <p>In Java write this:</p>
 * <pre>{@code
 * DownloadFile downloadFile = new DownloadFile().setText(" Download");
 * downloadFile.download("text.docx")
 * }</pre>
 */
public class DownloadFile extends WebLocator implements Download {

    public DownloadFile() {
        setClassName("DownloadFile");
        setBaseCls("btn");
        setTag("button");
    }

    /**
     * @param container parent
     */
    public DownloadFile(WebLocator container) {
        this();
        setContainer(container);
    }

    public DownloadFile(WebLocator container, String label) {
        this(container);
        setLabel(label);
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
        if (WebDriverConfig.isSilentDownload()) {
            fileName = WebDriverConfig.getDownloadPath() + File.separator + fileName;
            File file = new File(fileName);
            return FileUtils.waitFileIfIsEmpty(file) && fileName.equals(file.getAbsolutePath());
        } else {
            return RunExe.getInstance().download(fileName);
        }
    }

    public void openBrowse() {
        WebDriver driver = WebDriverConfig.getDriver();
        focus();
        Actions builder = new Actions(driver);
        builder.moveToElement(currentElement).build().perform();
        builder.click().perform();
    }

    public boolean isDisabled(){
        String cls = getAttributeClass();
        return (cls != null && cls.contains("disabled")) || getAttribute("disabled") != null;
    }
}