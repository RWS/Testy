package com.sdl.selenium.extjs6.window;

import com.sdl.selenium.extjs6.button.Button;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageBox extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageBox.class);

    public static String OK_TEXT = "OK";
    public static String CANCEL_TEXT = "Cancel";
    public static String YES_TEXT = "Yes";
    public static String NO_TEXT = "No";

    private Button okButton = new Button(this, OK_TEXT);
    private Button cancelButton = new Button(this, CANCEL_TEXT);
    private Button yesButton = new Button(this, YES_TEXT);
    private Button noButton = new Button(this, NO_TEXT);

    private MessageBox() {
        setClassName("MessageBox");
        setBaseCls("x-message-box");
        setExcludeClasses("x-hidden-offsets");
        WebLocator header = new WebLocator().setClasses("x-header");
        setTemplateTitle(new WebLocator(header));
        setTemplate("msg", "count(*[contains(concat(' ', @class, ' '), ' x-window-body ')]//*[text()='%s']) > 0");
    }

    public static void main(String[] args) {
        MessageBox messageBox = new MessageBox("Delete", "Delete Api key 2");
        LOGGER.debug(messageBox.getXPath());
    }

    public MessageBox(String title) {
        this();
        setTitle(title, SearchType.EQUALS);
    }

    public MessageBox(String title, String msg) {
        this(title);
        setTemplateValue("msg", msg);
    }

    public Button getOkButton() {
        return okButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public Button getYesButton() {
        return yesButton;
    }

    public Button getNoButton() {
        return noButton;
    }

    public String getMessage() {
        WebLocator mbTextElement = new WebLocator(this).setClasses("x-window-text");
        return mbTextElement.getHtmlText();
    }

    public boolean press(String buttonText) {
        Button button;
        if (OK_TEXT.equals(buttonText)) {
            button = okButton;
        } else if (CANCEL_TEXT.equals(buttonText)) {
            button = cancelButton;
        } else if (YES_TEXT.equals(buttonText)) {
            button = yesButton;
        } else if (NO_TEXT.equals(buttonText)) {
            button = noButton;
        } else {
            button = new Button(this, buttonText);
        }
        return press(button);
    }

    private boolean press(final Button button) {
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
