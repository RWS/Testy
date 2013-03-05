package com.extjs.selenium.button;

import com.sdl.selenium.web.button.SelectFilesHandler;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class HtmlFileUploadButton extends SelectFilesHandler {

    private static final Logger logger = Logger.getLogger(HtmlFileUploadButton.class);

    public HtmlFileUploadButton() {
//        setClassName("HtmlFileUploadButton");
        //logger.debug(getClassName() + "() constructor");
    }

    public HtmlFileUploadButton(WebLocator container, String text) {
        this();
        //setContainer(container);
        WebLocator button = new Button(container, text);
        setButtonElement(button);
    }
}
