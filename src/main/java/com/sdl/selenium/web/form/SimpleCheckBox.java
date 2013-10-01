package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class SimpleCheckBox extends WebLocator {
    private static final Logger logger = Logger.getLogger(SimpleCheckBox.class);

    public SimpleCheckBox() {
        setClassName("SimpleCheckBox");
        setTag("input");
    }

    public SimpleCheckBox(WebLocator container) {
        this();
        setContainer(container);
    }

    public SimpleCheckBox(String id) {
        this();
        setId(id);
    }

    public String getItemPath(boolean disabled) {
        String selector = getBasePathSelector();
        return "//" + getTag() + "[@type='checkbox'" + ("".equals(selector) ? "" : " and " + selector) + "]";
    }

    public boolean isSelected() {
        return ready() && executor.isSelected(this);
    }
}