package com.sdl.selenium.web.button;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.WebLocator;

// TODO class this should be in extjs3 or changed ExtJsComponent dependency
public class FlashUploadButton extends SelectFilesHandler {

    public FlashUploadButton() {
        WebLocator buttonElement = new WebLocator();
        buttonElement.setTag("object");
        setButtonElement(buttonElement);
    }

    public FlashUploadButton(Locator container, String cls) {
        this();
        getButtonElement().setClasses(cls);
        getButtonElement().setContainer(container);
    }

    public FlashUploadButton(Locator container) {
        this(container, "swfupload");
    }

    public FlashUploadButton(String label, Locator container) {
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
