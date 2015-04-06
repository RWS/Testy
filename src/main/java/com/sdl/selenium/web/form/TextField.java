package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.WebLocatorAbstractBuilder;
import com.sdl.selenium.web.utils.Utils;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextField extends WebLocator implements ITextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextField.class);

    private String type;

    public TextField() {
        setClassName("SimpleTextField");
        setTag("input");
        setTemplate("input-type", "@type='%s'");
    }

    public TextField(WebLocator container) {
        this();
        setContainer(container);
    }

    public TextField(String id) {
        this();
        setId(id);
    }

    @Deprecated
    public String getType() {
        return type;
    }

    @Deprecated
    public <T extends WebLocatorAbstractBuilder> T setType(String type) {
        this.type = type;
        setTemplateValue("input-type", type);
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