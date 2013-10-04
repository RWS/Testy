package com.sdl.selenium.web.form;

public interface ITextField {

    /**
     * Set value to DOM element
     * @param value
     * @return
     */
    boolean setValue(String value);

    /**
     * Get value from DOM element
     * @return
     */
    String getValue();

}
