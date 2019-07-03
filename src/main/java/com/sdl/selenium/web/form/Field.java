package com.sdl.selenium.web.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public abstract class Field extends WebLocator implements IField {

    /**
     * @param value       value
     * @param searchTypes accept only SearchType.EQUALS, SearchType.CONTAINS, SearchType.STARTS_WITH, SearchType.TRIM
     * @return current element
     */
    public <T extends IField> T setPlaceholder(String value, SearchType... searchTypes) {
        setAttribute("placeholder", value, searchTypes);
        return (T) this;
    }

    public String getError() {
        WebLocator error = new WebLocator(this).setRoot("/../../../../").setClasses("x-form-error-wrap");
        return error.getText();
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
}