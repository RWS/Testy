package com.sdl.selenium.extjs3.button;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.button.SelectFilesHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlFileUploadButton extends SelectFilesHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(HtmlFileUploadButton.class);

    public HtmlFileUploadButton() {
//        withClassName("HtmlFileUploadButton");
        //logger.debug(getClassName() + "() constructor");
    }

    public HtmlFileUploadButton(WebLocator container, String text) {
        this();
        //withContainer(container);
        WebLocator button = new Button(container, text);
        setButtonElement(button);
    }
}
