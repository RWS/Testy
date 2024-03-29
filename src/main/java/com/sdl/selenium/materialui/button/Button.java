package com.sdl.selenium.materialui.button;

import com.sdl.selenium.materialui.menu.Menu;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Button extends com.sdl.selenium.web.button.Button {

    public Button() {
        setClassName("Button");
        setType("button");
    }

    public Button(WebLocator container) {
        this();
        setContainer(container);
    }

    public Button(WebLocator container, String text, SearchType... searchTypes) {
        this(container);
        if (searchTypes.length == 0) {
            searchTypes = new SearchType[]{SearchType.EQUALS};
        }
        setText(text, searchTypes);
    }

    public <T extends com.sdl.selenium.web.button.Button> T setIconCls(String iconCls) {
        WebLocator svgIcon = new WebLocator().setLocalName("svg").setClasses(iconCls);
        setChildNodes(svgIcon);
        return (T) this;
    }

    public boolean clickOnMenu(String option, SearchType... searchTypes) {
        click();
        Menu menu = new Menu();
        return menu.clickOnMenu(option, searchTypes);
    }
}
