package com.extjs.selenium.form;

import com.extjs.selenium.ExtJsComponent;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Radio extends ExtJsComponent {

    private static final Logger logger = LoggerFactory.getLogger(Radio.class);

    public Radio() {
        setClassName("Radio");
        setTag("input");
        setBaseCls("x-form-radio");
        setLabelPosition("/../");
        setTemplate("text", "@value='%s'");
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

    public boolean isSelected() {
        return isElementPresent() && executor.isSelected(this);
    }
}