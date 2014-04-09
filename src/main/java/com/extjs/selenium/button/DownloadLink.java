package com.extjs.selenium.button;

import com.extjs.selenium.ExtJsComponent;
import com.sdl.bootstrap.button.Download;
import com.sdl.bootstrap.button.RunExe;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.File;

public class DownloadLink extends ExtJsComponent implements Download {
    private static final Logger logger = Logger.getLogger(DownloadLink.class);

    public DownloadLink() {
        setClassName("DownloadLink");
        setTag("a");
    }

    public DownloadLink(WebLocator container) {
        this();
        setContainer(container);
    }

    public DownloadLink(WebLocator container, String text) {
        this(container);
        setText(text, SearchType.EQUALS);
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
     * Download file with AutoIT. Work only on FireFox.
     * Use only this: button.download("C:\\TestSet.tmx");
     * return true if the downloaded file is the same one that is meant to be downloaded, otherwise returns false.
     * @param filePath e.g. "C:\\TestSet.tmx"
     */
    @Override
    public boolean download(String filePath) {
        openBrowse();
        return RunExe.getInstance().download(filePath);
    }

    /**
     * Download file.
     * return true if the downloaded file is the same one that is meant to be downloaded, otherwise returns false.
     * @param filePath e.g. "D:\\temp\\text.docx""
     */
    public boolean assertDownload(String filePath) {
        logger.debug("WebDriverConfig.isSalientDownload()=" + WebDriverConfig.isSalientDownload());
        openBrowse();
        File file = new File(filePath);
        return FileUtils.waitFor(file, 10) &&
                file.exists() && filePath.equals(file.getAbsolutePath());
    }

    private void openBrowse(){
        WebDriver driver = WebDriverConfig.getDriver();
        driver.switchTo().window(driver.getWindowHandle());
        focus();
        Actions builder = new Actions(driver);
        builder.moveToElement(currentElement).build().perform();
        builder.click().build().perform();
        driver.switchTo().defaultContent();
    }
}
