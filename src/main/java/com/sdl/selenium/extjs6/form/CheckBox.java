package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.utils.config.WebLocatorConfig;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICheck;

public class CheckBox extends WebLocator implements ICheck {

    public CheckBox() {
        setClassName("CheckBox");
        if ("6.7.0".equals(WebLocatorConfig.getExtJsVersion())) {
            setTag("input");
            setType("checkbox");
        } else {
            setBaseCls("x-form-checkbox");
        }
    }

    public CheckBox(WebLocator container) {
        this();
        setContainer(container);
    }

    public CheckBox(WebLocator container, String label, SearchType... searchTypes) {
        this(container);
        if (searchTypes.length == 0) {
            searchTypes = new SearchType[]{SearchType.DEEP_CHILD_NODE_OR_SELF};
        }
        setLabel(label, searchTypes);
    }

    public CheckBox(String boxLabel, WebLocator container) {
        this(container);
        setLabel(boxLabel);
        if ("6.7.0".equals(WebLocatorConfig.getExtJsVersion())) {
            setLabelPosition("/..//");
        } else {
            setLabelPosition("/../");
        }
    }

    @Override
    public boolean isSelected() {
        if ("6.7.0".equals(WebLocatorConfig.getExtJsVersion())) {
            return executor.isSelected(this);
        } else {
            WebLocator el = new WebLocator(this).setElPath("/../input");
            String select = el.getAttribute("aria-checked");
            return select != null && select.contains("true");
        }
    }

    @Override
    public boolean isEnabled() {
        String cls = getAttributeClass();
        return (cls != null && !cls.contains("disabled")) || getAttribute("disabled") == null;
    }
}