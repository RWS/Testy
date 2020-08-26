package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.utils.config.WebLocatorConfig;
import com.sdl.selenium.web.IWebLocator;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.WebLocatorAbstractBuilder;

public class Radio extends WebLocatorAbstractBuilder implements Cloneable, IWebLocator {
    private String version;

    public Radio() {
        setClassName("Radio");
        setTag("input");
        setBaseCls("x-form-cb-input");
        setAttribute("role", "radio");
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
        if ("6.7.0".equals(getVersion()) || "6.6.0".equals(getVersion())) {
            checked = executor.isSelected(this);
        } else {
            checked = "true".equals(getAttribute("aria-checked", instant));
        }
        return checked;
    }
}