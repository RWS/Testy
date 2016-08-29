package com.sdl.selenium.jqueryui.window;

import com.sdl.selenium.web.WebLocator;

public class Window extends WebLocator {

    public Window() {
        withClassName("Window");
        withBaseCls("ui-dialog ui-widget ui-widget-content");
        withStyle("display: block;");
        withTemplate("title", "count(.//*[text()='%s']) > 0");
    }

    public Window(String title) {
        this();
        withTitle(title);
    }

    public Window(String title, boolean isInternationalized) {
        this();
        withTitle(title, isInternationalized);
    }

    public String getMessageWindow() {
        WebLocator webLocator = new WebLocator(this).withClasses("ui-dialog-content ui-widget-content");
        return webLocator.getText();
    }

    public boolean pressOK() {
        return press("Ok");
    }

    public boolean press(String textButton) {
        WebLocator webLocator = new WebLocator(this).withElxPath("//button[count(.//*[text()='" + textButton + "']) > 0]");
        return webLocator.click();
    }

    public boolean pressOK(String msg) {
        if (ready()) {
            if (msg.equals(getMessageWindow())) {
                return pressOK();
            }
        }
        return false;
    }

    public boolean press(String msg, String textButton, boolean containsMsg) {
        if (ready()) {
            if(containsMsg ? getMessageWindow().contains(msg) : msg.equals(getMessageWindow())) {
                return press(textButton);
            }
        }
        return false;
    }

    public boolean press(String msg, String textButton) {
        return press( msg, textButton, false);
    }
}

