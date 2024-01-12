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
        WebLocator input = new WebLocator(this);
        boolean checked;
        if ("6.7.0".equals(getVersion()) || "6.6.0".equals(getVersion()) || "7.7.0".equals(getVersion())) {
            input.setElPath("//input");
            checked = executor.isSelected(input);
        } else {
            input.setElPath("/../input");
            checked = "true".equals(input.getAttribute("aria-checked", instant));
        }
        return checked;
    }
}