package com.extjs.selenium.button;

import com.sdl.bootstrap.button.RunExe;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.interactions.Actions;

public class UploadButton extends Button {

    public UploadButton() {
        setClassName("UploadButton");
        defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE);
    }

    public UploadButton(WebLocator container) {
        this();
        setContainer(container);
    }

    public UploadButton(WebLocator container, String text) {
        this(container);
        setText(text, SearchType.EQUALS);
    }

    public boolean upload(String [] filePath){
        return upload(this, filePath);
    }

    public boolean upload(String [] filePath, Long timeout){
        return upload(this, filePath, timeout);
    }

    private boolean upload(WebLocator el, String[] filePath) {
        browse(el);
        return RunExe.getInstance().upload(filePath);
    }

    private boolean upload(WebLocator el, String[] filePath, Long timeout) {
        browse(el);
        return RunExe.getInstance().upload(filePath, timeout);
    }

    private void browse(WebLocator el) {
        driver.switchTo().window(driver.getWindowHandle());
        el.focus();
        Actions builder = new Actions(driver);
        builder.moveToElement(el.currentElement).build().perform();
        builder.click().perform();
        driver.switchTo().defaultContent();
    }
}
