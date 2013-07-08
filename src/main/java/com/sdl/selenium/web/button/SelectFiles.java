package com.sdl.selenium.web.button;

import com.extjs.selenium.button.Button;
import com.sdl.bootstrap.button.RunExe;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;
import org.openqa.selenium.interactions.Actions;

public class SelectFiles extends Button {

    private static final Logger logger = Logger.getLogger(SelectFiles.class);

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
        builder.click().build().perform();
        driver.switchTo().defaultContent();
        return RunExe.getInstance().download(filePath);
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
