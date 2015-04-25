package com.sdl.selenium.extjs3.button;

import com.sdl.selenium.bootstrap.button.Download;
import com.sdl.selenium.bootstrap.button.RunExe;
import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.By;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.File;

public class DownloadLink extends ExtJsComponent implements Download {

    public DownloadLink(By ...bys) {
        getPathBuilder().defaults(By.tag("a"), By.text("", SearchType.EQUALS)).init(bys);
    }

    public DownloadLink(WebLocator container) {
        this(By.container(container));
    }

    public DownloadLink(WebLocator container, String text) {
        this(By.container(container), By.text(text, SearchType.EQUALS));
    }

    /**
     * Wait for the element to be activated when there is deactivation mask on top of it
     *
     * @param seconds time
     */
    @Override
    public boolean waitToActivate(int seconds) {
        return getPath().contains("ext-ux-livegrid") || super.waitToActivate(seconds);
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

    private void openBrowse() {
        WebDriver driver = WebDriverConfig.getDriver();
        focus();
        Actions builder = new Actions(driver);
        builder.moveToElement(currentElement).build().perform();
        builder.click().perform();
    }
}
