package com.sdl.selenium.web.window;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.WebLocator;

public class SimpleWindow extends WebLocator {

    public SimpleWindow() {
        setClassName("SimpleWindow");
        setBaseCls("ui-dialog ui-widget ui-widget-content");
        setStyle("display: block;");
    }

    public SimpleWindow(String title) {
        this();
        setTitle(title);
    }

    public String getItemPath(boolean disable) {
        String selector = getBasePathSelector();
        if (hasTitle()) {
            selector += " and count(.//*[text()='" + getTitle() + "']) > 0";
        }
        selector = Utils.fixPathSelector(selector);
        return "//*[" + selector + "]";
    }

    public String getMessageWindow() {
        WebLocator webLocator = new WebLocator(this).setCls("ui-dialog-content ui-widget-content");
        return webLocator.getHtmlText();
    }

    public boolean pressOK() {
        return press("Ok");
    }

    public boolean press(String textButton) {
        WebLocator webLocator = new WebLocator(this).setElPath("//button[count(.//*[text()='" + textButton + "']) > 0]");
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

