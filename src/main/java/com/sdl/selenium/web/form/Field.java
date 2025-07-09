package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;

import java.time.Duration;

public abstract class Field extends WebLocator implements IField {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Field.class);

    /**
     * Retrieves the error message associated with this field, if present.
     *
     * @return the error message text, or an empty string if no error is present
     */
    public String getError() {
        WebLocator error = new WebLocator(this).setRoot("/../../../../").setClasses("x-form-error-wrap");
        return error.getText();
    }

    /**
     * Clicks the icon associated with this field, if the field is ready.
     *
     * @param icon the name or identifier of the icon to click
     * @return true if the icon was successfully clicked, false otherwise
     */
    public boolean clickIcon(String icon) {
        if (ready()) {
            WebLocator iconLocator = getTriggerEl(this, icon);
            iconLocator.setRender(Duration.ofMillis(500));
            return iconLocator.click();
        } else {
            log.warn("clickIcon: field is not ready for use: {}", this);
            return false;
        }
    }

    /**
     * Checks if the field is editable (i.e., does not have the 'readonly' attribute set to true).
     *
     * @return true if the field is editable, false otherwise
     */
    public boolean isEditable() {
        return !"true".equals(getAttribute("readonly"));
    }

    /**
     * Checks if the field is enabled (i.e., does not have the 'disabled' attribute set to true).
     *
     * @return true if the field is enabled, false otherwise
     */
    public boolean isEnabled() {
        return !"true".equals(getAttribute("disabled"));
    }

    /**
     * Sets the value of the field.
     *
     * @param value the value to set in the field
     * @return true if the value was set successfully, false otherwise
     *
     * <pre>
     * Example usage:
     *   field.setValue("test value");
     * </pre>
     */
    public boolean setValue(String value) {
        return executor.setValue(this, value);
    }

    /**
     * Retrieves the current value of the field.
     *
     * @return the value of the field as a String
     */
    public String getValue() {
        return executor.getValue(this);
    }

    /**
     * Retrieves the label text associated with this field.
     *
     * @return the label text, or an empty string if no label is found
     */
    public String getLabel() {
        WebLocator child = new WebLocator().setTag("label");
        WebLocator parent = new WebLocator(this).setRoot("//ancestor::").setChildNodes(child);
        WebLocator labelEl = new WebLocator(parent).setRoot("/").setTag("label").setClasses("x-form-item-label");
        return labelEl.getText();
    }
}