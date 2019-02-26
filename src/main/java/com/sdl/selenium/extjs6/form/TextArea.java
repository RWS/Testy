package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.SearchType;

public class TextArea extends TextField {

    public TextArea() {
        setClassName("TextArea");
        setTag("textarea");
    }

    public TextArea(Locator container) {
        this();
        setContainer(container);
    }

    public TextArea(Locator container, String label) {
        this(container);
        setLabel(label, SearchType.DEEP_CHILD_NODE_OR_SELF);
    }
}