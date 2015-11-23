package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
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
            LOGGER.info("showMenu for {}; result: {}", toString(), object);
            Utils.sleep(200);
            return (Boolean) object;
        }
        LOGGER.debug("id is: {}", id);
        return false;
    }

//    public static void main(String[] args) {
//        Button button = new Button(null, "OK");
//        LOGGER.debug(button.getXPath());
//    }
}
