package com.sdl.selenium.web.button;

import com.sdl.selenium.web.WebLocator;

public class UploadButton extends WebLocator {

    public UploadButton() {
        withClassName("UploadButton");
        withTag("input");
    }

    public UploadButton(WebLocator container) {
        this();
        withContainer(container);
    }

    public UploadButton(WebLocator container, String id) {
        this(container);
        withId(id);
    }

    public boolean uploadFile(String path) {
        return sendKeys(path) != null;
    }

    /*public void uploadFileWithJS(String path, String id) {
        executeScript("return (function(id){var d=document.getElementById(id);d.style.visibility='visible';d.style.height='1px'; d.style.width='1px'; d.style.opacity=1;return d.getAttribute('style') != ''}('" + id + "'));");
        sendKeys(path);
    }*/
}
