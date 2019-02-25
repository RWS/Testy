package com.sdl.selenium.web.form;

import com.sdl.selenium.web.Editable;
import com.sdl.selenium.web.ILocator;
import com.sdl.selenium.web.SearchType;

public interface ITextField extends ILocator, Editable {

    /**
     * Set value to DOM element
     * @param value TextField
     * @return true
     */
    boolean setValue(String value);

    /**
     * Get value from DOM element
     * @return value
     */
    String getValue();

    <T extends ITextField> T setPlaceholder(final String value, SearchType...searchTypes);
}