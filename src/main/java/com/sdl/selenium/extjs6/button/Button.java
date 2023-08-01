package com.sdl.selenium.extjs6.button;

import com.google.common.base.Strings;
import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class Button extends com.sdl.selenium.web.button.Button {

    public Button() {
        setClassName("Button");
        setBaseCls("x-btn");
        setTag("a");
        getPathBuilder().defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    public Button(WebLocator container) {
        this();
        setContainer(container);
    }

    public Button(WebLocator container, String text, SearchType... searchTypes) {
        this(container);
        if (searchTypes.length == 0) {
            searchTypes = new SearchType[]{SearchType.EQUALS};
        }
        setText(text, searchTypes);
    }

    public boolean showMenu() {
        final String id = getAttributeId();
        if (!Strings.isNullOrEmpty(id)) {
            String script = "return (function(){var b = Ext.getCmp('" + id + "'); if(b) {b.showMenu(); return true;} return false;})()";
            Object object = WebLocatorUtils.doExecuteScript(script);
            return (Boolean) object;
        }
        return false;
    }

    public boolean isEnabled() {
        String cls = getAttributeClass();
        return cls != null && !cls.contains("x-btn-disabled");
    }

    public boolean click() {
        logIfButtonIsDisabled();
        return super.click();
    }

    public boolean doClick(boolean showLog) {
        logIfButtonIsDisabled();
        return super.doClick(showLog);
    }

    public String toString() {
        return getPathBuilder().itemToString();
    }
}