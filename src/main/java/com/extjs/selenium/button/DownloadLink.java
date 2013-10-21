package com.extjs.selenium.button;

import com.extjs.selenium.ExtJsComponent;
import com.sdl.bootstrap.button.RunExe;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;
import org.openqa.selenium.interactions.Actions;

public class DownloadLink extends ExtJsComponent {

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
     * @param seconds
     */
    @Override
    public boolean waitToActivate(int seconds) {
        return getPath().contains("ext-ux-livegrid") || super.waitToActivate(seconds);
    }

    /**
     * Download file with AutoIT. Work only on FireFox.
     * Use only this: button.download(new String[]{"C:\\downloadAndCancel.exe", "TestSet.tmx"});
     * return true if the downloaded file is the same one that is meant to be downloaded, otherwise returns false.
     * @param filePath
     */
    public boolean download(String[] filePath) {
        driver.switchTo().window(driver.getWindowHandle());
        focus();
        Actions builder = new Actions(driver);
        builder.moveToElement(currentElement).build().perform();
        builder.click().perform();
        driver.switchTo().defaultContent();
        return RunExe.getInstance().download(filePath);
    }
}
