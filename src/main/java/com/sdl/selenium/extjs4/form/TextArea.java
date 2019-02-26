package com.sdl.selenium.extjs4.form;

import com.sdl.selenium.web.SearchType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextArea extends TextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextArea.class);

    public TextArea() {
        setClassName("TextArea");
    }

    public TextArea(Locator container){
        this();
        setContainer(container);
    }

    public TextArea(Locator container, String label) {
        this(container);
        setLabel(label, SearchType.DEEP_CHILD_NODE);
    }
}
