package com.extjs.selenium.tab;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated pachage "com.extjs.selenium*" is deprecated, please use new package "com.sdl.selenium.extjs3"
 */
public class TabPanel extends com.sdl.selenium.extjs3.tab.TabPanel {
    public TabPanel(String text) {
        super(text);
    }

    public TabPanel(WebLocator container, String text) {
        super(container, text);
    }
}
