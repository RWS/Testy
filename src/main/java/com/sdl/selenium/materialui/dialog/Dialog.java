package com.sdl.selenium.materialui.dialog;

import com.sdl.selenium.materialui.button.Button;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Dialog extends WebLocator {

    public Dialog() {
        setClassName("Dialog");
        setBaseCls("MuiDialog-root");
        setExcludeClasses("MuiModal-hidden");
        WebLocator header = new WebLocator().setTag("h2").setClasses("MuiTypography-root");
        setTemplateTitle(header);
    }

    public Dialog(String title, SearchType... searchTypes) {
        this();
        setTitle(title, searchTypes);
    }

    public Dialog(String title, String message) {
        this(title);
        WebLocator content = new WebLocator().setText(message, SearchType.DEEP_CHILD_NODE_OR_SELF);
        setChildNodes(content);
    }

    public boolean press(String buttonName) {
        return new Button(this, buttonName).click();
    }
}
