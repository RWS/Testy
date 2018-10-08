package com.sdl.selenium.extjs6.window;

import com.sdl.selenium.web.SearchType;

public class Window extends com.sdl.selenium.extjs4.window.Window {

    public Window() {
        super();
    }

    public Window(String title, SearchType... searchTypes) {
        this();
        setTitle(title, searchTypes.length == 0 ? new SearchType[]{SearchType.EQUALS} : searchTypes);
    }
}