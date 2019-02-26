package com.sdl.selenium.web.button;

import com.sdl.selenium.bootstrap.button.Upload;
import com.sdl.selenium.web.Locator;

public class UploadButton extends Locator implements Upload {

    public UploadButton() {
        setClassName("UploadButton");
        setTag("input");
    }

    public UploadButton(Locator container) {
        this();
        setContainer(container);
    }

    public UploadButton(Locator container, String id) {
        this(container);
        setId(id);
    }

    public boolean upload(String path) {
        return executor().sendKeys(this, path);
    }

    /*public void uploadFileWithJS(String path, String id) {
        executeScript("return (function(id){var d=document.getElementById(id);d.style.visibility='visible';d.style.height='1px'; d.style.width='1px'; d.style.opacity=1;return d.getAttribute('style') != ''}('" + id + "'));");
        sendKeys(path);
    }*/
}
