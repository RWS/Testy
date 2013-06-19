package com.sdl.bootstrap.form;

import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class Span extends WebLocator {
    private static final Logger logger = Logger.getLogger(Span.class);

    public Span() {
        setClassName("Span");
        setBaseCls("uneditable-input");
        setTag("span");
    }

    public Span(WebLocator container) {
        this();
        setContainer(container);
    }

    public Span(WebLocator container, String label) {
        this(container);
        setLabel(label);
    }

    /*public String getItemPath(boolean disabled) {
        String selector = getBasePathSelector();
        selector = Utils.fixPathSelector(selector);
        selector = "//" + getTag() + (selector.length() > 0 ? ("[" + selector + "]") : "");
        return selector + "//input[@type='checkbox']";
    }*/
}