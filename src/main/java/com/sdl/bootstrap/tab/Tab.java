package com.sdl.bootstrap.tab;

import com.sdl.selenium.web.WebLocator;
/**
 * @deprecated package "com.sdl.bootstrap.*" is deprecated, please use new package "com.sdl.selenium.bootstrap.*"
 */
public class Tab extends com.sdl.selenium.bootstrap.tab.Tab {
    public Tab(String text) {
        super(text);
    }

    public Tab(WebLocator container, String text) {
        super(container, text);
    }
}
