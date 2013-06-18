package com.sdl.bootstrap.form;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.SimpleTextField;
import org.apache.log4j.Logger;

public class Form extends SimpleTextField {
    private static final Logger logger = Logger.getLogger(Form.class);

    public Form() {
        setClassName("Form");
        setTag("form");
    }

    public Form(WebLocator container) {
        this();
        setContainer(container);
    }

    public Form(WebLocator container, String title) {
        this(container);
        setTitle(title);
    }

    public String getItemPath(boolean disabled) {
        String selector = getBasePathSelector();
        if (hasTitle()) {
            selector += "count(.//legend[text()='" + getTitle() + "']) > 0";
        }
        selector = Utils.fixPathSelector(selector);
        selector = "//" + getTag() + (selector.length() > 0 ? ("[" + selector + "]") : "");
        return selector;
    }
}