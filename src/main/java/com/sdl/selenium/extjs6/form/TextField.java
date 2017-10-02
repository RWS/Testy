package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TextField extends com.sdl.selenium.web.form.TextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextField.class);

    public TextField() {
        setLabelPosition("//following-sibling::*//");
        setBaseCls("x-form-text");
    }

    public TextField(WebLocator container) {
        this();
        setContainer(container);
    }

    public TextField(WebLocator container, String label) {
        this(container);
        setLabel(label, SearchType.DEEP_CHILD_NODE);
    }

    // methods
    public boolean assertSetValue(String value) {
        boolean setted = setValue(value);
        assertThat(setted, is(true));
        return true;
    }

    public WebLocator getTriggerEl(String icon) {
        return new WebLocator(this).setRoot("/").setTag("parent::*/parent::*/*").setClasses("x-form-" + icon).setInfoMessage(this + " -> " + icon);
    }

    public boolean clickIcon(String icon) {
        if (ready()) {
            WebLocator iconLocator = getTriggerEl(icon);
            iconLocator.setRenderMillis(500);
            try {
                return iconLocator.click();
            } catch (Exception e) {
                LOGGER.error("Exception on clickIcon: " + e.getMessage());
                return false;
            }
        } else {
            LOGGER.warn("clickIcon : field is not ready for use: " + this);
        }
        return false;
    }

    /**
     * @return true is the element doesn't have attribute readonly
     */
    public boolean isEditable() {
        return !"true".equals(getAttribute("readonly"));
    }

    /**
     * @return true is the element does have attribute disabled
     */
    public boolean isDisabled() {
        return "true".equals(getAttribute("disabled"));
    }

    public boolean isEnabled() {
        return !"true".equals(getAttribute("disabled"));
    }

    public String getError(){
        WebLocator error = new WebLocator(this).setRoot("/../../../../").setClasses("x-form-error-wrap");
        return error.getText();
    }
}
