package com.sdl.selenium.extjs6.panel;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class Accordion extends Panel {

    public Accordion() {
        setClassName("Accordion");
        setBaseCls("x-accordion-item");
        WebLocator header = new WebLocator().setClasses("x-title").setRoot("//");
        setTemplateTitle(new WebLocator(header));
    }

    public Accordion(WebLocator container) {
        this();
        setContainer(container);
    }

    public Accordion(WebLocator container, String title, SearchType... searchTypes) {
        this(container);
        if (searchTypes.length == 0) {
            searchTypes = new SearchType[]{SearchType.EQUALS};
        }
        setTitle(title, searchTypes);
    }
}