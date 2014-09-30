package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.WebLocatorAbstractBuilder;
import com.sdl.selenium.web.utils.Utils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;

public class SimpleTextField extends WebLocator implements ITextField {
    private static final Logger LOGGER = Logger.getLogger(SimpleTextField.class);

    private String type;

    public SimpleTextField() {
        setClassName("SimpleTextField");
        setTag("input");
        setTemplate("input-type", "@type='%s'");
    }

    public SimpleTextField(WebLocator container) {
        this();
        setContainer(container);
    }

    public SimpleTextField(String id) {
        this();
        setId(id);
    }

    public String getType() {
        return type;
    }

    public <T extends WebLocatorAbstractBuilder> T setType(String type) {
        this.type = type;
        String key = "input-type";
        setElPathSuffix(key, applyTemplate(key, type));
        return (T) this;
    }

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

    public String getValue() {
        return executor.getValue(this);
    }
}