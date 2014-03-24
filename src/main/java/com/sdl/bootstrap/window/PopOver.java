package com.sdl.bootstrap.window;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class PopOver extends WebLocator {

    private WebLocator container;

    private PopOver(){
        setInfoMessage("PopOver");
        container = new WebLocator().setCls("popover-title");
    }

    public PopOver(String title, String message) {
        this();
        container.setText(title, SearchType.EQUALS);
        setContainer(container);
        setElPath("//following-sibling::*[@class='popover-content' and text()='" + message + "']");

    }

    public PopOver(String title) {
        this();
        container.setText(title, SearchType.EQUALS);
        setContainer(container);
        setElPath("//following-sibling::*[@class='popover-content']");
    }
}