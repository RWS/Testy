package com.sdl.selenium.web.form;

import com.sdl.selenium.web.Editable;
import com.sdl.selenium.web.IWebLocator;

public interface ITextField extends IWebLocator, Editable {

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
