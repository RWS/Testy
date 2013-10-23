package com.extjs.selenium.window;

public class MessageBoxWindow extends Window {

    public MessageBoxWindow() {
        super(true);
        setClassName("MessageBoxWindow");
        setInfoMessage("MessageBoxWindow");
        setClasses("x-window-dlg");
    }

    public MessageBoxWindow(String title) {
        this();
        setTitle(title);
    }
}
