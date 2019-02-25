package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ButtonNew extends AButtonNew {

    public ButtonNew() {
//        setClassName("Button");
        setBaseCls("x-btn");
        setTag("a");
        setTemplate("icon-cls", "count(.//*[contains(concat(' ', @class, ' '), ' %s ')]) > 0");
        getXPathBuilder().defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    public ButtonNew(WebLocator container) {
        this();
        setContainer(container);
    }

    public ButtonNew(WebLocator container, String text) {
        this(container);
        setText(text, SearchType.EQUALS);
    }

    public static void main(String[] args) {
        AButtonNew b = new ButtonNew(null, "Cancel");
        log.debug("{}", b.getXPath());
        b.setBaseCls("basicCls");
        log.debug("{}", b.getXPath());
    }
}