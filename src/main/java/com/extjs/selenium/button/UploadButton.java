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

    private boolean upload(WebLocator el, String[] filePath) {
        driver.switchTo().window(driver.getWindowHandle());
        el.focus();
        Actions builder = new Actions(driver);
        builder.moveToElement(el.currentElement).build().perform();
        builder.click().perform();
        driver.switchTo().defaultContent();
        return RunExe.getInstance().upload(filePath);
    }
}
