package com.sdl.bootstrap.button;

import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.io.IOException;

public class UploadFile extends WebLocator {
    private static final Logger logger = Logger.getLogger(UploadFile.class);

    public UploadFile() {
        setClassName("UploadFile");
        setCls("fileupload");
        setTag("div");
    }

    /**
     * @param container
     */
    public UploadFile(WebLocator container) {
        this();
        setContainer(container);
    }

    public UploadFile(WebLocator container, String label) {
        this(container);
        setLabel(label);
    }

    /**
     * Upload file with AutoIT.
     * Use only this: button.browseWithAutoIT("Browse", new String[] {"C:\\upload.exe", "C:\\text.txt"});
     * @param text
     * @param filePath
     */
    public void upload(String text, String[] filePath) {
        upload("fileupload-new", text, filePath);
    }

    public void change(String text, String[] filePath) {
        upload("fileupload-exists", text, filePath);
    }

    public void change(String[] filePath) {
        upload("fileupload-exists", "Change", filePath);
    }

    public void upload(String cls, String text, String[] filePath) {
        driver.switchTo().window(driver.getWindowHandle());
        WebLocator upload = new WebLocator(this).setTag("span").setCls(cls).setText(text);
        upload.focus();
        Actions builder = new Actions(driver);
        builder.moveToElement(upload.currentElement).build().perform();
        builder.click().build().perform();
        driver.switchTo().defaultContent();
        try {
            Process process = Runtime.getRuntime().exec(filePath[0] + " " + filePath[1] + " " + uploadName());
            if (0 != process.waitFor()) {
                Assert.fail();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String uploadName() {
        return WebLocator.driver instanceof FirefoxDriver ? "File Upload" : "Open";
    }

    public String uploadedNameFile() {
        WebLocator upload = new WebLocator(this).setTag("span").setCls("fileupload-preview");
        return upload.getHtmlText();
    }
}