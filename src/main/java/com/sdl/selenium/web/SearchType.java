package com.sdl.selenium.web;

public enum SearchType {

    EQUALS,

    CONTAINS,

    STARTS_WITH,

    /**
     * will use : normalize-spaces on text()
     */
    TRIM,

    CHILD_NODE,

    DEEP_CHILD_NODE

}
