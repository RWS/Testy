package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.utils.config.WebLocatorConfig;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class Radio extends WebLocator {
    private String version;

    public Radio() {
        setClassName("Radio");
        setTag("span");
        setBaseCls("x-form-radio");
        setLabelPosition("/../");
    }

    public Radio(WebLocator container) {
        this();
        setContainer(container);
    }

    public Radio(WebLocator container, String label, SearchType... searchTypes) {
        this(container);
        setLabel(label, searchTypes);
    }

    private String getVersion() {
        return version == null ? WebLocatorConfig.getExtJsVersion() : version;
    }

    public <T extends Radio> T setVersion(String version) {
        this.version = version;
        return (T) this;
    }

    public boolean isSelected() {
        return isSelected(false);
    }

    public boolean isSelected(boolean instant) {
        boolean checked;
        if ("6.7.0".equals(getVersion()) || "6.6.0".equals(getVersion()) || "7.7.0".equals(getVersion())) {
            String aClass = getAttributeClass();
            checked = aClass != null && aClass.contains("x-form-cb-checked");
        } else {
            WebLocator input = new WebLocator(this).setTag("input");
            checked = "true".equals(input.getAttribute("aria-checked", instant));
        }
        return checked;
    }
}