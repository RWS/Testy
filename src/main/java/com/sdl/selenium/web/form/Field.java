package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;

import java.time.Duration;

public abstract class Field extends WebLocator implements IField {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Field.class);

    public String getError() {
        WebLocator error = new WebLocator(this).setRoot("/../../../../").setClasses("x-form-error-wrap");
        return error.getText();
    }

    public boolean clickIcon(String icon) {
        if (ready()) {
            WebLocator iconLocator = getTriggerEl(this, icon);
            iconLocator.setRender(Duration.ofMillis(500));
            return iconLocator.click();
        } else {
            log.warn("clickIcon : field is not ready for use: " + this);
        }
        return false;
    }

    /**
     * @return true is the element doesn't have attribute readonly
     */
    public boolean isEditable() {
        return !"true".equals(getAttribute("readonly"));
    }

    public boolean isEnabled() {
        return !"true".equals(getAttribute("disabled"));
    }

    public String getValue() {
        return executor.getValue(this);
    }
}