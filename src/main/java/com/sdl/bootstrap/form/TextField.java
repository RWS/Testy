package com.sdl.bootstrap.form;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.SimpleTextField;
import org.apache.log4j.Logger;

public class TextField extends SimpleTextField {
    private static final Logger logger = Logger.getLogger(TextField.class);

    public TextField() {
        setClassName("TextField");
        setTag("input");
    }

    public TextField(WebLocator container) {
        this();
        setContainer(container);
    }

    public TextField(WebLocator container, String label) {
        this(container);
        setLabel(label);
    }

    public String getItemPath(boolean disabled) {
        String selector = getBasePathSelector() + " and @type='text'";
        selector = Utils.fixPathSelector(selector);
        selector = "//" + getTag() + (selector.length() > 0 ? ("[" + selector + "]") : "");
        return selector;
    }
}