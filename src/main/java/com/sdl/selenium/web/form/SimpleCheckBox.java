package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class SimpleCheckBox extends WebLocator implements ICheck {
    private static final Logger logger = Logger.getLogger(SimpleCheckBox.class);

    public SimpleCheckBox() {
        setClassName("SimpleCheckBox");
        setTag("input");
        setElPathSuffix("type", "@type='checkbox'");
    }

    public SimpleCheckBox(WebLocator container) {
        this();
        setContainer(container);
    }

    public SimpleCheckBox(String id) {
        this();
        setId(id);
    }

    @Override
    public boolean isSelected() {
        return ready() && executor.isSelected(this);
    }
}