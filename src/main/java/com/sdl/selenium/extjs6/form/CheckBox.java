package com.sdl.selenium.extjs6.form;

import com.google.common.base.Strings;
import com.sdl.selenium.utils.config.WebLocatorConfig;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.XPathBuilder;
import com.sdl.selenium.web.form.ICheck;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class CheckBox extends WebLocator implements ICheck {

    private String version;
    private boolean isBoxLabel = false;

    public CheckBox() {
        setClassName("CheckBox");
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

    public CheckBox(String boxLabel, WebLocator container) {
        this(container);
        setLabel(boxLabel);
        isBoxLabel = true;
    }

    public <T extends CheckBox> T setVersion(String version) {
        this.version = version;
        return (T) this;
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

    public String getXPath() {
        XPathBuilder pathBuilder = getPathBuilder();
        if (!Strings.isNullOrEmpty(version)) {
            if ("6.7.0".equals(version)) {
                pathBuilder.setTag("*".equals(pathBuilder.getTag()) ? "input" : pathBuilder.getTag());
                pathBuilder.setType("checkbox");
                if (isBoxLabel) {
                    pathBuilder.setLabelPosition(WebLocatorConfig.getDefaultLabelPosition().equals(pathBuilder.getLabelPosition()) ? "/..//" : pathBuilder.getLabelPosition());
                }
            } else {
                pathBuilder.setBaseCls("x-form-checkbox");
                if (isBoxLabel) {
                    pathBuilder.setLabelPosition(WebLocatorConfig.getDefaultLabelPosition().equals(pathBuilder.getLabelPosition()) ? "/../" : pathBuilder.getLabelPosition());
                }
            }
        }
        return super.getXPath();
    }
}