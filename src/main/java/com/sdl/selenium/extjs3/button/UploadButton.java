package com.sdl.selenium.extjs3.button;

import com.sdl.selenium.bootstrap.button.Upload;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class UploadButton extends Button implements Upload {

    public UploadButton() {
        withClassName("UploadButton");
    }

    public UploadButton(WebLocator container) {
        this();
        withContainer(container);
    }

    public UploadButton(WebLocator container, String text) {
        this(container);
        withText(text, SearchType.EQUALS);
    }

    @Override
    public boolean upload(String ...filePath){
        return upload(this, filePath);
    }

    private boolean upload(WebLocator el, String ...filePath) {
        return executor.browse(el) && executor.upload(filePath);
    }
}
