package com.sdl.selenium.extjs3.form;

import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.MultiThreadClipboardUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;

public class TextField extends com.sdl.selenium.web.form.TextField {
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

    public TextField(Locator container) {
        this();
        setContainer(container);
    }

    public TextField(Locator container, String label) {
        this(container);
        setLabel(label);
    }

    public TextField(String name, Locator container) {
        this(container);
        setName(name);
    }

    // methods
    public boolean pasteInValue(String value) {
        ready();
        if (value != null) {
            doClear();
            MultiThreadClipboardUtils.copyString(value);
            MultiThreadClipboardUtils.pasteString(this);
            LOGGER.info("Set value(" + this + "): " + value + "'");
            return true;
        }
        return false;
    }

    public boolean setValue(String value) {
        ready();
        boolean setted = executor().setValue(this, value);
        assertThat("Could not setValue on : " + this, setted);
        return true;
    }

    public boolean doSetValue(String value) {
        boolean setted = false;
        if (ready()) {
            setted = executor().setValue(this, value);
            if (!setted) {
                LOGGER.warn("Could not setValue on {}", this);
            }
        } else {
            LOGGER.info("Element was not rendered {}", toString());
        }
        return setted;
    }

    /**
     * getValue using xPath only, depending on the parameter
     *
     * @return string
     */
    public String getValue() {
        ready();
        return executor().getValue(this);
    }

    public String getTriggerPath(String icon) {
        return "/parent::*//*[contains(@class,'x-form-" + icon + "-trigger')]";
    }

    public boolean clickIcon(String icon) {
        ready();
        String triggerPath = getTriggerPath(icon);
        WebLocator iconLocator = new WebLocator(this).setElPath(triggerPath);
        iconLocator.setRenderMillis(500);
        iconLocator.setInfoMessage(this + " -> trigger-" + icon);
        iconLocator.click();
        return true;
    }

    public boolean isEnabled() {
        return !"true".equals(getAttribute("disabled"));
    }
}