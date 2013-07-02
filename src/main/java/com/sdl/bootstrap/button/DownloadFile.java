package com.sdl.bootstrap.button;

import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;
import org.openqa.selenium.interactions.Actions;

public class DownloadFile extends WebLocator {
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

    /**
     * Download file with AutoIT. Work only on FireFox.
     * Use only this: button.download(testSet, new String[]{"C:\\download.exe", "TestSet.tmx"});
     *
     * @param filePath
     */
    public boolean download(String[] filePath) {
        driver.switchTo().window(driver.getWindowHandle());
        focus();
        Actions builder = new Actions(driver);
        builder.moveToElement(currentElement).build().perform();
        builder.click().build().perform();
        driver.switchTo().defaultContent();
        return new RunExe().download(filePath);
    }
}