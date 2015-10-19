package com.sdl.selenium.web.button;

import com.sdl.selenium.bootstrap.button.Upload;
import com.sdl.selenium.web.WebLocator;
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
        return executor.browse(el) && executor.upload(filePath);
    }
}