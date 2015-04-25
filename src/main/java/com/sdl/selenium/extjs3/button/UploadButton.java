package com.sdl.selenium.extjs3.button;

import com.sdl.selenium.bootstrap.button.RunExe;
import com.sdl.selenium.bootstrap.button.Upload;
import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class UploadButton extends Button implements Upload {

    public UploadButton(By...bys) {
        super(bys);
    }

    public UploadButton(WebLocator container) {
        this(By.container(container));
    }

    public UploadButton(WebLocator container, String text) {
        this(By.container(container), By.text(text));
    }

    @Override
    public boolean upload(String ...filePath){
        return upload(this, filePath);
    }

    private boolean upload(WebLocator el, String ...filePath) {
        browse(el);
        return RunExe.getInstance().upload(filePath);
    }

    private void browse(WebLocator el) {
        WebDriver driver = WebDriverConfig.getDriver();
        el.focus();
        Actions builder = new Actions(driver);
        builder.moveToElement(el.currentElement).perform();
        builder.click().perform();
    }
}
