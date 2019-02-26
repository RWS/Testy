package com.sdl.selenium.extjs6.panel;

import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class Accordion extends Panel {

    public Accordion() {
        setClassName("Accordion");
        setBaseCls("x-accordion-item");
        WebLocator header = new WebLocator().setClasses("x-title").setRoot("//");
        setTemplateTitle(new WebLocator(header));
    }

    public Accordion(Locator container) {
        this();
        setContainer(container);
    }

    public Accordion(Locator container, String title) {
        this(container);
        setTitle(title, SearchType.EQUALS);
    }
}