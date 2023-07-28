package com.sdl.selenium.materialui.list;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class List extends WebLocator {

    public List() {
        setClassName("List");
        setTag("ul");
    }

    public List(WebLocator container) {
        this();
        setContainer(container);
    }

    public boolean clickOn(String item, final SearchType... searchTypes) {
        WebLocator itemEl = new WebLocator().setTag("li").setText(item, searchTypes);
        return itemEl.click();
    }
}
