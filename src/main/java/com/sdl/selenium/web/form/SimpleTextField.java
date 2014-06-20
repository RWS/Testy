package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.WebLocatorAbstractBuilder;
import org.apache.log4j.Logger;

public class SimpleTextField extends WebLocator implements ITextField {
    private static final Logger logger = Logger.getLogger(SimpleTextField.class);

    private String type;

    public SimpleTextField() {
        setClassName("SimpleTextField");
        setTag("input");
        setTemplates("input-type", "@type='%s'");
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

    public boolean setValue(String value) {
        return executor.setValue(this, value);
    }

    public String getValue() {
        return executor.getValue(this);
    }
}