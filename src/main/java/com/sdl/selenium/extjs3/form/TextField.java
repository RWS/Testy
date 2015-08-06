package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ITextField;
import com.sdl.selenium.web.utils.Utils;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextField extends ExtJsComponent implements ITextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextField.class);

    public TextField() {
        setClassName("TextField");
        setTag("input");
        setElPathSuffix("not-hidden", "not(@type='hidden')");
        setLabelPosition("//following-sibling::*//");
    }

    public TextField(String cls) {
        this();
        setClasses(cls);
    }

    public TextField(WebLocator container) {
        this();
        setContainer(container);
    }

    public TextField(WebLocator container, String label) {
        this(container);
        setLabel(label);
    }

    public TextField(String name, WebLocator container) {
        this(container);
        setName(name);
    }

    /**
     * @param value value
     * @param searchTypes accept only SearchType.EQUALS, SearchType.CONTAINS, SearchType.STARTS_WITH, SearchType.TRIM
     * @return current element
     */
    public <T extends ITextField> T setPlaceholder(final String value, SearchType ...searchTypes) {
        setAttribute("placeholder", value, searchTypes);
        return (T) this;
    }

    // methods
    public boolean pasteInValue(String value) {
        if (ready()) {
            if (value != null) {
                currentElement.clear();
                Utils.copyToClipboard(value);
                currentElement.sendKeys(Keys.CONTROL, "v");
                LOGGER.info("Set value(" + this + "): " + value + "'");
                return true;
            }
        } else {
            LOGGER.warn("setValue : field is not ready for use: " + toString());
        }
        return false;
    }

    public boolean setValue(String value) {
        return executor.setValue(this, value);
    }

    public boolean assertSetValue(String value) {
        boolean setted = setValue(value);
        if (!setted) {
            Assert.fail("Could not setValue on : " + this);
        }
        return true;
    }

    /**
     * getValue using xPath only, depending on the parameter
     *
     * @return string
     */
    public String getValue() {
        return executor.getValue(this);
    }

    public String getTriggerPath(String icon) {
        return "/parent::*//*[contains(@class,'x-form-" + icon + "-trigger')]";
    }

    public boolean clickIcon(String icon) {
        if (ready()) {
            String triggerPath = getTriggerPath(icon);
            WebLocator iconLocator = new WebLocator(this).setElPath(triggerPath);
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