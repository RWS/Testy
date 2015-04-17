package com.sdl.selenium.web.window;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebLocator;

public class Window extends WebLocator {

    public Window(By...bys) {
        getPathBuilder().defaults(By.baseCls("ui-dialog ui-widget ui-widget-content"), By.style("display: block;"), By.template("title", "count(.//*[text()='%s']) > 0")).init(bys);
    }

    public Window(String title) {
        this(By.title(title));
    }

    public String getMessageWindow() {
        WebLocator webLocator = new WebLocator(By.container(this), By.classes("ui-dialog-content ui-widget-content"));
        return webLocator.getHtmlText();
    }

    public boolean pressOK() {
        return press("Ok");
    }

    public boolean press(String textButton) {
        WebLocator webLocator = new WebLocator(By.container(this), By.xpath("//button[count(.//*[text()='" + textButton + "']) > 0]"));
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
            if (containsMsg ? getMessageWindow().contains(msg) : msg.equals(getMessageWindow())) {
                return press(textButton);
            }
        }
        return false;
    }

    public boolean press(String msg, String textButton) {
        return press(msg, textButton, false);
    }
}

