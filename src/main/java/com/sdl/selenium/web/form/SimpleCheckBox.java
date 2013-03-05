package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class SimpleCheckBox extends WebLocator {
    private static final Logger logger = Logger.getLogger(SimpleCheckBox.class);

    public SimpleCheckBox() {
        setClassName("SimpleCheckBox");
    }

    public SimpleCheckBox(String id) {
        this();
        setId(id);
    }

    public boolean isSelected(){
        return isElementPresent() && executor.isSelected(this);
    }
}
