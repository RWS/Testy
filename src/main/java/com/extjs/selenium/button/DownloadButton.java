package com.extjs.selenium.button;

import com.sdl.bootstrap.button.Download;
import com.sdl.bootstrap.button.RunExe;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.interactions.Actions;

import java.io.File;

public class DownloadButton extends Button implements Download {

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
     * Use only this: button.download("C:\\downloadAndCancel.exe", "TestSet.tmx");
     * return true if the downloaded file is the same one that is meant to be downloaded, otherwise returns false.
     *
     * @param filePath e.g. "C:\\downloadAndCancel.exe", "TestSet.tmx"
     */
    @Override
    public boolean download(String ...filePath) {
        openBrowse();
        return RunExe.getInstance().download(filePath);
    }

    /**
     * Download file.
     * return true if the downloaded file is the same one that is meant to be downloaded, otherwise returns false.
     * @param filePath e.g. "D:\\temp\\text.docx""
     */
    public boolean assertDownload(String filePath) {
        openBrowse();
        return new File(filePath).exists();
    }

    private void openBrowse(){
        driver.switchTo().window(driver.getWindowHandle());
        focus();
        Actions builder = new Actions(driver);
        builder.moveToElement(currentElement).build().perform();
        builder.click().build().perform();
        driver.switchTo().defaultContent();
    }
}
