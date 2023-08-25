package com.sdl.selenium.materialui.menu;

import com.sdl.selenium.materialui.list.List;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Menu extends WebLocator implements IMenu {

    public Menu() {
        setClassName("Menu");
        setBaseCls("MuiMenu-root");
        setClasses("MuiPopover-root");
    }
    private final List list = new List(this).setBaseCls("MuiMenu-list");

    public boolean clickOnMenu(String option, SearchType... searchTypes) {
        ready();
        return list.clickOn(option, searchTypes);
    }
}
