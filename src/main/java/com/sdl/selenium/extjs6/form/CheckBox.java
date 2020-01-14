package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.utils.config.WebLocatorConfig;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.WebLocatorAbstractBuilder;
import com.sdl.selenium.web.form.Field;
import com.sdl.selenium.web.form.ICheck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckBox extends Field implements ICheck {

    private String version;
    private boolean isBoxLabel = false;

    public CheckBox() {
        setClassName("CheckBox");
        setVersion(getVersion());
    }

    public CheckBox(WebLocator container) {
        this();
        setContainer(container);
    }

    public CheckBox(WebLocator container, String label, SearchType... searchTypes) {
        this(container);
        if (searchTypes.length == 0) {
            searchTypes = new SearchType[]{SearchType.DEEP_CHILD_NODE_OR_SELF};
        } else {
            List<SearchType> types = new ArrayList<>(Arrays.asList(searchTypes));
            types.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
            searchTypes = types.toArray(new SearchType[0]);
        }
        setLabel(label, searchTypes);
    }

    public CheckBox(String boxLabel, WebLocator container, SearchType... searchTypes) {
        this(container);
        setLabel(boxLabel, searchTypes);
        isBoxLabel = true;
        setVersion(getVersion());
    }

    public String getVersion() {
        return version == null ? WebLocatorConfig.getExtJsVersion() : version;
    }

    public <T extends WebLocatorAbstractBuilder> T setVersion(String version) {
        this.version = version;
        if (isBoxLabel) {
            setLabelPosition(getProp("checkbox.boxLabel"));
        }
        setTag(getProp("checkbox.tag"));
        setType(getProp("checkbox.type"));
        setBaseCls(getProp("checkbox.baseCls"));
        return (T) this;
    }

    public String getProp(String prop) {
        prop = "Ext." + prop;
        String property = WebLocatorConfig.getProperty(prop + "." + version);
        // TODO find closest preview version
        //String closestVersion = "";

        if (property == null) {
            property = WebLocatorConfig.getProperty(prop);
        }
        return property;
    }

    @Deprecated
    public boolean isSelected() {
        return isChecked();
    }

    @Override
    public boolean isChecked() {
        if ("6.7.0".equals(getVersion()) || "6.6.0".equals(getVersion())) {
            return executor.isSelected(this);
        } else {
            WebLocator el = new WebLocator(this).setElPath("/../input");
            String select = el.getAttribute("aria-checked");
            return select != null && select.contains("true");
        }
    }
}