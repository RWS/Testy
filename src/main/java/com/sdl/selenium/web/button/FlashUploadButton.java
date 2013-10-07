package com.sdl.selenium.web.button;

import com.extjs.selenium.ExtJsComponent;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class FlashUploadButton extends SelectFilesHandler {

    private static final Logger logger = Logger.getLogger(FlashUploadButton.class);

    public FlashUploadButton() {
        WebLocator buttonElement = new WebLocator();
        buttonElement.setTag("object");
        setButtonElement(buttonElement);
    }

    public FlashUploadButton(WebLocator container, String cls) {
        this();
        getButtonElement().setClasses(cls);
        getButtonElement().setContainer(container);
    }

    public FlashUploadButton(WebLocator container) {
        this(container, "swfupload");
    }

    public FlashUploadButton(String label, WebLocator container) {
        this();
        getButtonElement().setClasses("swfupload");
        ExtJsComponent button = new ExtJsComponent(container);
        button.setLabel(label);
        getButtonElement().setContainer(button);
    }

    public void setElPath(String elPath){
        getButtonElement().setElPath(elPath);
    }
}
