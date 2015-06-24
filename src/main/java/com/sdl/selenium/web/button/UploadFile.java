package com.sdl.selenium.web.button;

import com.sdl.selenium.bootstrap.button.RunExe;
import com.sdl.selenium.bootstrap.button.Upload;
import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UploadFile extends WebLocator implements Upload {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadFile.class);

    public UploadFile() {
        setClassName("UploadFile");
    }

    /**
     * @param container parent
     */
    public UploadFile(WebLocator container) {
        this();
        setContainer(container);
    }

    @Override
    public boolean upload(String... filePath) {
        return upload(this, filePath);
    }

    public boolean upload(WebLocator el, String... filePath) {
        return browse(el) && RunExe.getInstance().upload(filePath);
    }

    private boolean browse(WebLocator el) {
        try {
            WebDriver driver = WebDriverConfig.getDriver();
            el.focus();
            Actions builder = new Actions(driver);
            builder.moveToElement(el.currentElement).perform();
            builder.click().perform();
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
    }
}