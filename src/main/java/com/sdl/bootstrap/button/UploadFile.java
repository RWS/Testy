package com.sdl.bootstrap.button;

import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class UploadFile extends WebLocator implements Upload {
    private static final Logger LOGGER = Logger.getLogger(UploadFile.class);

    public UploadFile() {
        setClassName("UploadFile");
        setClasses("fileupload");
        setTag("div");
    }

    /**
     * @param container parent
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
     * Use only this: button.upload("Browse", "C:\\upload.exe", "C:\\text.txt");
     *
     * @param text     button text
     * @param filePath e.g. "C:\\upload.exe", "C:\\text.txt"
     */
    public boolean upload(String text, String... filePath) {
        WebLocator upload = new WebLocator(this).setTag("span").setClasses("fileupload-new").setText(text);
        return upload(upload, filePath);
    }

    public void change(String... filePath) {
        change("Change", filePath);
    }

    public boolean change(String text, String... filePath) {
        WebLocator upload = new WebLocator(this).setTag("span").setClasses("fileupload-exists").setText(text);
        return upload(upload, filePath);
    }

    /**
     * Upload file with AutoIT.
     * Use only this: button.upload("C:\\upload.exe", "C:\\text.txt");
     *
     * @param filePath new String[] {"C:\\upload.exe", "C:\\text.txt"}
     */
    @Override
    public boolean upload(String... filePath) {
        WebLocator uploadButton = new WebLocator(this).setTag("span").setClasses("fileupload-new").setElPathSuffix("icon-folder-open", "count(.//i[@class='icon-folder-open']) > 0");
        return upload(uploadButton, filePath);
    }

    public boolean reUpload(String... filePath) {
        WebLocator uploadButton = new WebLocator(this).setTag("span").setClasses("fileupload-exists").setElPathSuffix("icon-refresh", "count(.//i[@class='icon-refresh']) > 0");
        return upload(uploadButton, filePath);
    }

    public boolean removeFile() {
        WebLocator removeButton = new WebLocator(this).setTag("a").setClasses("fileupload-exists").setElPathSuffix("icon-trash", "count(.//i[@class='icon-trash']) > 0");
        return removeButton.clickAt();
    }

    public String uploadedNameFile() {
        WebLocator upload = new WebLocator(this).setTag("span").setClasses("fileupload-preview");
        return upload.getHtmlText();
    }

    public boolean upload(WebLocator el, String... filePath) {
        browse(el);
        return RunExe.getInstance().upload(filePath);
    }

    private void browse(WebLocator el) {
        WebDriver driver = WebDriverConfig.getDriver();
//        driver.switchTo().window(driver.getWindowHandle());
        el.focus();
        Actions builder = new Actions(driver);
        builder.moveToElement(el.currentElement).build().perform();
        builder.click().build().perform();
//        driver.switchTo().defaultContent();
    }
}