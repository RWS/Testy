package com.sdl.selenium.extjs4.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextField extends com.sdl.selenium.web.form.TextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextField.class);

    public TextField() {
        setClassName("TextField");
        setLabelPosition("//following-sibling::*//");
    }

    public TextField(WebLocator container) {
        this();
        setContainer(container);
    }

    public TextField(WebLocator container, String label) {
        this(container);
        setLabel(label, SearchType.DEEP_CHILD_NODE);
    }

    @Deprecated
    public String getTriggerPath(String icon) {
        return "/parent::*/parent::*//*[contains(@class,'x-form-" + icon + "-trigger')]";
    }

    @Deprecated
    public boolean clickIcon(String icon) {
        if (ready()) {
            WebLocator iconLocator = new WebLocator(this).setElPath(getTriggerPath(icon));
            iconLocator.setRenderMillis(500);
            iconLocator.setInfoMessage(this + " -> trigger-" + icon);
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
}
