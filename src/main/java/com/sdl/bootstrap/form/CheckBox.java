package com.sdl.bootstrap.form;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.SimpleTextField;
import org.apache.log4j.Logger;

public class CheckBox extends SimpleTextField {
    private static final Logger logger = Logger.getLogger(CheckBox.class);

    public CheckBox() {
        setClassName("CheckBox");
        setTag("label");
    }

    public CheckBox(WebLocator container) {
        this();
        setContainer(container);
    }

    public CheckBox(WebLocator container, String label) {
        this(container);
        setText(label);
    }

    public String getItemPath(boolean disabled) {
        String selector = getBasePathSelector();
        selector = Utils.fixPathSelector(selector);
        selector = "//" + getTag() + (selector.length() > 0 ? ("[" + selector + "]") : "");
        return selector + "//input[@type='checkbox']";
    }

    public boolean isSelected(){
        return isElementPresent() && executor.isSelected(this);
    }
}