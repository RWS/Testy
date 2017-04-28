package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Button extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Button.class);

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

    public Button(WebLocator container, String text) {
        this(container);
        setText(text, SearchType.EQUALS);
    }

    public boolean showMenu() {
        final String id = getAttributeId();
        if (id != null && !"".equals(id)) {
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
