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

    public <T extends WebLocatorAbstractBuilder> T getLocator() {
        return (T) this;
    }

    @Override
    public boolean waitToRender() {
        return waitToRender(getPathBuilder().getRender());
    }

    @Override
    public boolean waitToActivate() {
        return waitToActivate(getPathBuilder().getActivate());
    }

    private String getVersion() {
        return version == null ? WebLocatorConfig.getExtJsVersion() : version;
    }

    public <T extends Radio> T setVersion(String version) {
        this.version = version;
        return (T) this;
    }

    public boolean check(){
        WebLocator locator = new WebLocator(getLocator()).setRoot("/../../").setTag("span");
        return locator.click();
    }

    public boolean isSelected() {
        return isSelected(false);
    }

    public boolean isSelected(boolean instant) {
        boolean checked;
        if ("6.7.0".equals(getVersion()) || "6.6.0".equals(getVersion())) {
            checked = getExecutor().isSelected(getLocator());
        } else {
            checked = "true".equals(getExecutor().getAttribute(getLocator(), "aria-checked", instant));
        }
        return checked;
    }
}