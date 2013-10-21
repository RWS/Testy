package com.extjs.selenium.button;

import com.sdl.bootstrap.button.RunExe;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;
import org.openqa.selenium.interactions.Actions;

public class DownloadButton extends Button {

    private static final Logger logger = Logger.getLogger(DownloadButton.class);

    public DownloadButton() {
        setClassName("DownloadButton");
        defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE);
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
