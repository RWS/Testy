package com.sdl.selenium.extjs4.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextField extends com.sdl.selenium.web.form.TextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextField.class);

    public TextField() {
        withClassName("TextField");
        withLabelPosition("//following-sibling::*//");
    }

    public TextField(WebLocator container){
        this();
        withContainer(container);
    }

    public TextField(WebLocator container, String label) {
        this(container);
        withLabel(label, SearchType.DEEP_CHILD_NODE);
    }

    public String getTriggerPath(String icon) {
        return "/parent::*/parent::*//*[contains(@class,'x-form-" + icon + "-trigger')]";
    }

    public boolean clickIcon(String icon) {
        if (ready()) {
            WebLocator iconLocator = new WebLocator(this).withElxPath(getTriggerPath(icon));
            iconLocator.withRenderMillis(500);
            iconLocator.withInfoMessage(this + " -> trigger-" + icon);
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
}
