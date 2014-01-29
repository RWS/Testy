package com.sdl.bootstrap.form;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.SimpleTextField;
import org.apache.log4j.Logger;

public class CheckBox extends SimpleTextField {
    private static final Logger logger = Logger.getLogger(CheckBox.class);

    public CheckBox() {
        setClassName("CheckBox");
        setType("checkbox");
    }

    public CheckBox(WebLocator container) {
        this();
        setContainer(container);
    }

    public CheckBox(WebLocator container, String label) {
        this(container);
        setLabelTag("label");
        setLabel(label);
    }

    public CheckBox(String label, WebLocator container) {
        this(container);
        setText(label, SearchType.EQUALS);
    }

    public String getItemPath(boolean disabled) {
        String selector = getBasePathSelector();
        if (hasType()) {
            selector += " and @type='" + getType() + "'";
        }
        if (hasText()) {
            return "//" + getLabelTag() + "[" + Utils.fixPathSelector(getItemPathText()) + "]//" + getTag() + "[@type='" + getType() + "']";
        }
        selector = Utils.fixPathSelector(selector);
        return "//" + getTag() + (selector.length() > 0 ? ("[" + selector + "]") : "");
    }

    public boolean isSelected() {
        return isElementPresent() && executor.isSelected(this);
    }
}