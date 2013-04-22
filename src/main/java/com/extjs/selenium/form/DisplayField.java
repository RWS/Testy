package com.extjs.selenium.form;

import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class DisplayField extends TextField {
    private static final Logger logger = Logger.getLogger(DisplayField.class);

    public DisplayField() {
        setClassName("DisplayField");
        setBaseCls("x-form-display-field");
    }

    public DisplayField(WebLocator container, String label) {
        this();
        setContainer(container);
        setLabel(label);
    }

    public DisplayField(String name, WebLocator container) {
        this();
        setContainer(container);
        setName(name);
    }

    public String getItemPath(boolean disabled) {
        String selector = getBasePathSelector();
        if(selector != null && selector.length() > 0){
            selector = "//*["+ selector +"]";
        } else {
            selector = "//*";
        }
        return selector;
    }

    public boolean setValue(String value) {
        // TODO find better way (maybe extending Field not text field directly)
        logger.warn("can't set Value in display field");
        return false;
    }


    public String getValue() {
        String value = "";
        if(ready()){
            value = getHtmlText();
        } else {
            logger.warn("getValue : field is not ready for use: " + toString());
        }
        return value;
    }
}
