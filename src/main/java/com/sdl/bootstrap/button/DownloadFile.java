package com.sdl.bootstrap.button;

import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.File;

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
     * @param filePath e.g. "C:\\TestSet.tmx"
     */
    @Override
    public boolean download(String filePath) {
        openBrowse();
        if (WebDriverConfig.isSilentDownload()) {
            File file = new File(filePath);
            return FileUtils.waitFileIfIsEmpty(file) && filePath.equals(file.getAbsolutePath());
        } else {
            return RunExe.getInstance().download(filePath);
        }
    }

    private void openBrowse() {
        WebDriver driver = WebDriverConfig.getDriver();
        driver.switchTo().window(driver.getWindowHandle());
        focus();
        Actions builder = new Actions(driver);
        builder.moveToElement(currentElement).build().perform();
        builder.click().build().perform();
        driver.switchTo().defaultContent();
    }
}