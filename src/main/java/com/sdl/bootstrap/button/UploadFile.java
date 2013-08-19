package com.sdl.bootstrap.button;

import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;
import org.openqa.selenium.interactions.Actions;

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
     * Use only this: button.upload("Browse", new String[] {"C:\\upload.exe", "C:\\text.txt"});
     *
     * @param text
     * @param filePath
     */
    public boolean upload(String text, String[] filePath) {
        WebLocator upload = new WebLocator(this).setTag("span").setCls("fileupload-new").setText(text);
        return upload(upload, filePath);
    }

    public void change(String[] filePath) {
        change("Change", filePath);
    }

    public boolean change(String text, String[] filePath) {
        WebLocator upload = new WebLocator(this).setTag("span").setCls("fileupload-exists").setText(text);
        return upload(upload, filePath);
    }

    /**
     * Upload file with AutoIT.
     * Use only this: button.upload(new String[] {"C:\\upload.exe", "C:\\text.txt"});
     *
     * @param filePath new String[] {"C:\\upload.exe", "C:\\text.txt"}
     */
    public boolean upload(String[] filePath) {
        WebLocator uploadButton = new WebLocator(this, "//span[contains(@class,'fileupload-new') and count(.//i[@class='icon-folder-open']) > 0]");
        return upload(uploadButton, filePath);
    }

    public boolean reUpload(String[] filePath) {
        WebLocator uploadButton = new WebLocator(this, "//span[contains(@class,'fileupload-exists') and count(.//i[@class='icon-refresh']) > 0]");
        return upload(uploadButton, filePath);
    }

    public boolean removeFile() {
        WebLocator removeButton = new WebLocator(this, "//a[contains(@class,'fileupload-exists') and count(.//i[@class='icon-trash']) > 0]");
        return removeButton.clickAt();
    }

    public String uploadedNameFile() {
        WebLocator upload = new WebLocator(this).setTag("span").setCls("fileupload-preview");
        return upload.getHtmlText();
    }

    public boolean upload(WebLocator el, String[] filePath) {
        driver.switchTo().window(driver.getWindowHandle());
        el.focus();
        Actions builder = new Actions(driver);
        builder.moveToElement(el.currentElement).build().perform();
        builder.click().build().perform();
        driver.switchTo().defaultContent();
        return RunExe.getInstance().upload(filePath);
    }
}