package com.sdl.selenium.web.form;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.WebLocatorAbstractBuilder;
import org.apache.log4j.Logger;

public class SimpleTextField extends WebLocator implements ITextField {
    private static final Logger logger = Logger.getLogger(SimpleTextField.class);

    private String type;

    public SimpleTextField() {
        setClassName("SimpleTextField");
        setTag("input");
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
        return (T) this;
    }

    public Boolean hasType() {
        return type != null && !type.equals("");
    }

    public String getItemPath(boolean disabled) {
        String selector = getBasePathSelector();
        if (hasType()) {
            selector += " and @type='" + getType() + "'";
        }
        selector = Utils.fixPathSelector(selector);
        return "//" + getTag() + (selector.length() > 0 ? ("[" + selector + "]") : "");
    }

    public boolean setValue(String value) {
        if (value != null) {
            return executor.setValue(this, value);
        }
        return false;
    }


    /**
     * getValue using xPath
     *
     * @return
     */
    public String getValue() {
        return executor.getValue(this);
    }
}