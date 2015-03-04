package com.extjs.selenium;

import com.sdl.selenium.web.WebLocator;

/**
 * @deprecated pachage "com.extjs.selenium*" is deprecated, please use new package "com.sdl.selenium.extjs3"
 */
public class ExtJsComponent extends com.sdl.selenium.extjs3.ExtJsComponent {
    public ExtJsComponent() {
    }

    public ExtJsComponent(String cls) {
        super(cls);
    }

    public ExtJsComponent(WebLocator container) {
        super(container);
    }

    public ExtJsComponent(WebLocator container, String elPath) {
        super(container, elPath);
    }

    public ExtJsComponent(String cls, WebLocator container) {
        super(cls, container);
    }

    public ExtJsComponent(String text, String cls, WebLocator container) {
        super(text, cls, container);
    }
}