package com.extjs.selenium.button;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.button.SelectFiles;
import org.apache.log4j.Logger;

public class UploadButton extends SelectFiles {

    private static final Logger logger = Logger.getLogger(UploadButton.class);

    public UploadButton() {
        setClassName("UploadButton");
    }

    public UploadButton(WebLocator container) {
        this();
        setElPath(new Button(container).getPath());
    }

    public UploadButton(WebLocator container, String text) {
        this();

        setElPath(new Button(container, text).getPath());
    }

    public boolean upload(String [] filePath){
        return upload(this, filePath);
    }
}
