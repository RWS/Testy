package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ITextField;
import com.sdl.selenium.web.utils.Utils;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;

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
     * @param value       value
     * @param searchTypes accept only SearchType.EQUALS, SearchType.CONTAINS, SearchType.STARTS_WITH, SearchType.TRIM
     * @return current element
     */
    public <T extends ITextField> T setPlaceholder(final String value, SearchType... searchTypes) {
        setAttribute("placeholder", value, searchTypes);
        return (T) this;
    }

    // methods
    public boolean pasteInValue(String value) {
        assertReady();
        if (value != null) {
            doClear();
            Utils.copyToClipboard(value);
            executor.doSendKeys(this, Keys.CONTROL, "v");
            LOGGER.info("Set value(" + this + "): " + value + "'");
            return true;
        }
        return false;
    }

    public boolean setValue(String value) {
        assertReady();
        boolean setted = executor.setValue(this, value);
        assertThat("Could not setValue on : " + this, setted);
        return true;
    }

    public boolean doSetValue(String value) {
        boolean setted = false;
        if (ready()) {
            setted = executor.setValue(this, value);
            if (!setted) {
                LOGGER.warn("Could not setValue on {}", this);
            }
        } else {
            LOGGER.info("Element was not rendered {}", toString());
        }
        return setted;
    }

    /**
     * @deprecated use {@link #setValue(String)}
     * @param value
     * @return
     */
    public boolean assertSetValue(String value) {
        setValue(value);
        return true;
    }

    /**
     * getValue using xPath only, depending on the parameter
     *
     * @return string
     */
    public String getValue() {
        assertReady();
        return executor.getValue(this);
    }

    public String getTriggerPath(String icon) {
        return "/parent::*//*[contains(@class,'x-form-" + icon + "-trigger')]";
    }

    public boolean clickIcon(String icon) {
        assertReady();
        String triggerPath = getTriggerPath(icon);
        WebLocator iconLocator = new WebLocator(this).setElPath(triggerPath);
        iconLocator.setRenderMillis(500);
        iconLocator.setInfoMessage(this + " -> trigger-" + icon);
        iconLocator.assertClick();
        return true;
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