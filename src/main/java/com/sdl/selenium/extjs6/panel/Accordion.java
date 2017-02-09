package com.sdl.selenium.extjs6.panel;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Accordion extends Panel {
    private static final Logger LOGGER = LoggerFactory.getLogger(Accordion.class);

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

    public Accordion(WebLocator container, String title) {
        this(container);
        setTitle(title, SearchType.EQUALS);
    }
}
