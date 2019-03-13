package com.sdl.selenium.extjs6;

import com.sdl.selenium.utils.config.WebLocatorConfig;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import lombok.Getter;

@Getter
public abstract class ExtJsLocator extends WebLocator {
    private String version = WebLocatorConfig.getExtJsVersion();

    public ExtJsLocator() {
    }

    public ExtJsLocator(String version) {
        this();
        this.version = version;
    }

    public ExtJsLocator(WebLocator container) {
        this();
        setContainer(container);
    }

    public ExtJsLocator(String version, WebLocator container) {
        this(container);
        this.version = version;
    }

    public ExtJsLocator(WebLocator container, String label, SearchType... searchTypes) {
        this(container);
        setLabel(label, searchTypes);
    }

    public ExtJsLocator(String version, WebLocator container, String label, SearchType... searchTypes) {
        this(version, container);
        setLabel(label, searchTypes);
    }
}
