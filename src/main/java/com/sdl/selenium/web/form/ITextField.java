package com.sdl.selenium.web.form;

public interface ITextField extends IField {

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
}