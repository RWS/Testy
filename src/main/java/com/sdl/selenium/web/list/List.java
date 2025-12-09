package com.sdl.selenium.web.list;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

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

    public WebLocator getItemEl(String item, SearchType... searchTypes) {
        return new WebLocator(this).setTag("li").setText(item, searchTypes);
    }

    public ArrayList<String> getValues() {
        ArrayList<String> items = new ArrayList<>();
        WebLocator liEl = new WebLocator(this).setTag("li");
        int size = liEl.size();
        for (int i = 1; i <= size; i++) {
            liEl.setResultIdx(i);
            String text = liEl.getText();
            items.add(text);
        }
        return items;
    }
}
