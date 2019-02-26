package com.sdl.selenium.web.form;

import com.sdl.selenium.web.Editable;
import com.sdl.selenium.web.ILocator;
import com.sdl.selenium.web.Mousable;
import com.sdl.selenium.web.SearchType;

public interface ITextField extends ILocator, Editable, Mousable {

    /**
     * Set value to DOM element
     *
     * @param value TextField
     * @return true
     */
    boolean setValue(String value);

    /**
     * Get value from DOM element
     *
     * @return value
     */
    String getValue();

    boolean isEditable();

    /**
     * @param value       value
     * @param searchTypes accept only SearchType.EQUALS, SearchType.CONTAINS, SearchType.STARTS_WITH, SearchType.TRIM
     * @return current element
     */
    default <T extends ITextField> T setPlaceholder(final String value, SearchType... searchTypes) {
        setAttribute("placeholder", value, searchTypes);
        return (T) this;
    }
}