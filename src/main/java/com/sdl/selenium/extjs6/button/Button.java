package com.sdl.selenium.extjs6.button;

import com.google.common.base.Strings;
import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class Button extends WebLocator {

    public Button() {
        setClassName("Button");
        setBaseCls("x-btn");
        setTag("a");
        setTemplate("icon-cls", "count(.//*[contains(concat(' ', @class, ' '), ' %s ')]) > 0");
        getXPathBuilder().defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    public Button(WebLocator container) {
        this();
        setContainer(container);
    }

    public Button(WebLocator container, String text) {
        this(container);
        setText(text, SearchType.EQUALS);
    }

    private String iconCls;

    public String getIconCls() {
        return iconCls;
    }

    public <T extends Button> T setIconCls(final String iconCls) {
        this.iconCls = iconCls;
        setTemplateValue("icon-cls", iconCls);
        return (T) this;
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