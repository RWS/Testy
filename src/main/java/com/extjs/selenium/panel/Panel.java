package com.extjs.selenium.panel;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated pachage "com.extjs.selenium*" is deprecated, please use new package "com.sdl.selenium.extjs3"
 */
public class Panel extends com.sdl.selenium.extjs3.panel.Panel {
    public Panel() {
    }

    public Panel(String title) {
        super(title);
    }

    public Panel(WebLocator container) {
        super(container);
    }

    public Panel(WebLocator container, String title) {
        super(container, title);
    }

    public Panel(String cls, WebLocator container, String excludeClass) {
        super(cls, container, excludeClass);
    }
}
