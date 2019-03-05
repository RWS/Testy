package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class TextArea extends TextField {

    public TextArea() {
        setClassName("TextArea");
        setTag("textarea");
    }

    public TextArea(WebLocator container) {
        this();
        setContainer(container);
    }

    public TextArea(WebLocator container, String label, SearchType ... searchTypes) {
        this(container);
        if (searchTypes.length == 0) {
            searchTypes = new SearchType[]{SearchType.DEEP_CHILD_NODE_OR_SELF};
        }
        setLabel(label, searchTypes);
    }
}