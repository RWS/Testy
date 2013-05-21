package com.sdl.selenium.web.button;

import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class SimpleUploadButton extends WebLocator {
    private static final Logger logger = Logger.getLogger(SimpleUploadButton.class);

    public SimpleUploadButton() {
        setClassName("SimpleUploadButton");
        setTag("input");
    }

    public SimpleUploadButton(String id) {
        this();
        setId(id);
    }

    public void uploadFile(String path) {
        type(path);
    }
}
