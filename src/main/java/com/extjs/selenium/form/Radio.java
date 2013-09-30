package com.extjs.selenium.form;

import com.extjs.selenium.ExtJsComponent;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class Radio extends ExtJsComponent {

    private static final Logger logger = Logger.getLogger(Radio.class);

    public Radio() {
        setClassName("Radio");
        setTag("input");
        setBaseCls("x-form-radio");
        setLabelPosition("/..");
    }

    public Radio(String value) {
        this();
        setText(value);
    }

    public Radio(WebLocator container) {
        this();
        setContainer(container);
    }

    public Radio(WebLocator container, String name) {
        this(container);
        setName(name);
    }

    public Radio(String label, WebLocator container) {
        this(container);
        setLabel(label);
    }

    public String getItemPathText() {
        String selector = "";
        if (hasText()) {
            selector += " and @value='" + getText() + "'";
        }
        return selector;
    }

    public String afterItemPathCreated(String itemPath) {
        if (hasLabel()) {
            itemPath = itemPath + getLabelPosition() + getLabelPath();
        }
        itemPath = addPositionToPath(itemPath);
        return itemPath;
    }

    public boolean isSelected() {
        return isElementPresent() && executor.isSelected(this);
    }
}