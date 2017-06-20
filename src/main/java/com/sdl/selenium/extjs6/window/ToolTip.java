package com.sdl.selenium.extjs6.window;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToolTip extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToolTip.class);

    public ToolTip() {
        setInfoMessage("ToolTip");
        setClasses("x-tip");
        setAttribute("aria-hidden", "false");
    }

    public ToolTip(String message, SearchType... searchTypes) {
        this();
        WebLocator messageEl = new WebLocator(this).setClasses("x-tip-body").setText(message, searchTypes);
        setChildNodes(messageEl);
    }

    public String getMessage() {
        WebLocator messageEl = new WebLocator(this).setClasses("x-tip-body");
        return messageEl.getText();
    }

//    public static void main(String[] args) {
//        ToolTip t = new ToolTip("This email address will be used to receive notifications about your account.", SearchType.DEEP_CHILD_NODE_OR_SELF);
//        LOGGER.debug("{}", t.getXPath());
//    }
}