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
        WebLocator itemEl = getItemEl(item, searchTypes);
        return itemEl.click();
    }

    public boolean doClickOn(String item, final SearchType... searchTypes) {
        return getItemEl(item, searchTypes).doClick();
    }

    public WebLocator getItemEl(String item, SearchType... searchTypes) {
        return new WebLocator(this).setTag("li").setText(item, searchTypes);
    }
}
