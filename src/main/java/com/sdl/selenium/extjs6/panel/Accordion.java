package com.sdl.selenium.extjs6.panel;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Accordion extends Panel {
    private static final Logger LOGGER = LoggerFactory.getLogger(Accordion.class);

    public Accordion() {
        withClassName("Accordion");
        withBaseCls("x-accordion-item");
        WebLocator header = new WebLocator().withClasses("x-title").withRoot("//");
        withTemplateTitle(new WebLocator(header));
    }

    public Accordion(WebLocator container) {
        this();
        withContainer(container);
    }

    public Accordion(WebLocator container, String title) {
        this(container);
        withTitle(title, SearchType.EQUALS);
    }

    public Accordion(WebLocator container, String title, boolean isInternationalized) {
        this(container);
        withTitle(title, isInternationalized, SearchType.EQUALS);
    }
}
