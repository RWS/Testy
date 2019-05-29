package com.sdl.selenium.extjs6.window;

import com.sdl.selenium.extjs6.button.Button;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import lombok.Getter;

@Getter
public class MessageBox extends WebLocator {

    private Button okButton = new Button(this, "OK");
    private Button cancelButton = new Button(this, "Cancel");
    private Button yesButton = new Button(this, "Yes");
    private Button noButton = new Button(this, "No");

    private MessageBox() {
        setClassName("MessageBox");
        setBaseCls("x-message-box");
        setExcludeClasses("x-hidden-offsets");
        WebLocator header = new WebLocator().setClasses("x-header");
        setTemplateTitle(new WebLocator(header));
        setTemplate("msg", "count(//*[contains(concat(' ', @class, ' '), ' x-window-body ')]//*[text()='%s']) > 0");
    }

    public MessageBox(String title) {
        this();
        setTitle(title, SearchType.EQUALS);
    }

    public MessageBox(String title, String msg, SearchType... searchTypes) {
        this(title);
        WebLocator body = new WebLocator().setClasses("x-window-body");
        WebLocator textEl = new WebLocator(body).setText(msg, searchTypes);
        setElPathSuffix("msg", "count(" + textEl.getXPath() + ") > 0");
    }

    public String getMessage() {
        WebLocator mbTextElement = new WebLocator(this).setClasses("x-window-text");
        return mbTextElement.getText();
    }

    public boolean press(String buttonText) {
        Button button = new Button(this, buttonText);
        return press(button);
    }

    private boolean press(final Button button) {
        button.setVisibility(true);
        return button.click();
    }

    public boolean pressOK() {
        return press(okButton);
    }

    public boolean pressYes() {
        return press(yesButton);
    }

    public boolean pressNo() {
        return press(noButton);
    }

    public boolean close() {
        WebLocator close = new WebLocator(this).setClasses("x-tool-close");
        return close.click();
    }
}