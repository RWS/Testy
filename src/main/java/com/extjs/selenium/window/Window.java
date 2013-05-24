package com.extjs.selenium.window;

import com.extjs.selenium.Utils;
import com.extjs.selenium.panel.Panel;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class Window extends Panel {
    private static final Logger logger = Logger.getLogger(Window.class);

    private boolean modal;

    public boolean isModal() {
        return modal;
    }

    public void setModal(boolean modal) {
        this.modal = modal;
    }

    /**
     * Windows have default style="visibility: visible;"
     */
    public Window() {
        setClassName("Window");
        setBaseCls("x-window");
        setHeaderBaseCls(getBaseCls());
        // test for IE be cause of :
        // http://jira.openqa.org/browse/SEL-545
        // and http://code.google.com/p/selenium/issues/detail?id=1716
        if (!isIE()) {
            setStyle("visibility: visible;");
        }
    }

    public Window(Boolean modal) {
        this();
        this.modal = modal;
    }

    public Window(String title) {
        this();
        setTitle(title);
    }

    public Window(String title, Boolean modal) {
        this();
        this.modal = modal;
        setTitle(title);
    }

    public String getItemPath(boolean disabled) {
        String selector = getBasePathSelector();
        selector += getHeaderSelector();
        if (isModal()) {
            // test for IE be cause of :
            // http://jira.openqa.org/browse/SEL-545
            // and http://code.google.com/p/selenium/issues/detail?id=1716
            if (isIE()) {
                selector += " and preceding-sibling::*[contains(@class, 'ext-el-mask')]";
            } else {
                selector += " and preceding-sibling::*[contains(@class, 'ext-el-mask') and contains(@style, 'display: block')]";
            }
        }

        selector = Utils.fixPathSelector(selector);
        return "//*[" + selector + "]";
    }

    public String getTitleWindow() {
        ready();
        return new WebLocator(this, "//*[contains(@class, 'x-window-header-text')]").getHtmlText();
    }
}