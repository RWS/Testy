package com.sdl.selenium.web.link;

import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class WebLink extends WebLocator {
    private static final Logger logger = Logger.getLogger(WebLink.class);

    public WebLink() {
        setClassName("WebLink");
        setTag("a");
    }

    public WebLink(WebLocator container) {
        this();
        setContainer(container);
    }

    public WebLink(WebLocator container, String text) {
        this(container);
        setText(text);
    }
}
