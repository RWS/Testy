package com.sdl.selenium.web.button;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.WebElement;

public class Button extends WebLocator implements IButton {

    public Button() {
        setClassName("Button");
        setTag("button");
        setTemplate("icon-cls", "count(.//*[contains(concat(' ', @class, ' '), ' %s ')]) > 0");
    }

    public Button(WebLocator container) {
        this();
        setContainer(container);
    }

    public Button(WebElement webElement) {
        this();
        setWebElement(webElement);
    }

    public Button(WebLocator container, String text, SearchType... searchTypes) {
        this(container);
        if (searchTypes.length == 0) {
            searchTypes = new SearchType[]{SearchType.EQUALS};
        }
        setText(text, searchTypes);
    }

    private String iconCls;

    public String getIconCls() {
        return iconCls;
    }

    public <T extends Button> T setIconCls(final String iconCls) {
        this.iconCls = iconCls;
        setTemplateValue("icon-cls", iconCls);
        return (T) this;
    }
}
