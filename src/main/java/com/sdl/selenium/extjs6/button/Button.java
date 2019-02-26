package com.sdl.selenium.extjs6.button;

import com.google.common.base.Strings;
import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.SearchType;

public class Button extends com.sdl.selenium.web.button.Button {

    public Button() {
        setClassName("Button");
        setBaseCls("x-btn");
        setTag("a");
        getXPathBuilder().defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    public Button(Locator container) {
        this();
        setContainer(container);
    }

    public Button(Locator container, String text) {
        this(container, text, SearchType.EQUALS);
    }

    public Button(Locator container, String text, SearchType ... searchTypes) {
        this(container);
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
}