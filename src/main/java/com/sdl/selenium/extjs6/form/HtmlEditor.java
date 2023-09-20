package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

import java.util.Arrays;
import java.util.List;

public class HtmlEditor extends TextField {
    private WebLocator iframe = new WebLocator().setTag("iframe").setClasses("x-htmleditor-iframe");
    private WebLocator body = new WebLocator().setTag("body");

    public HtmlEditor() {
        setClassName("HtmlEditor");
        setTag("*");
        setBaseCls("x-html-editor-input");
    }

    public HtmlEditor(WebLocator container) {
        this();
        this.setContainer(container);
    }

    public HtmlEditor(WebLocator container, String label, SearchType... searchTypes) {
        this(container);
        if (searchTypes.length == 0) {
            searchTypes = new SearchType[]{SearchType.DEEP_CHILD_NODE_OR_SELF};
        } else {
            List<SearchType> types = Arrays.asList(searchTypes);
            types.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
            searchTypes = types.toArray(new SearchType[0]);
        }

        this.setLabel(label, searchTypes);
    }

    public boolean setValue(String value) {
        WebLocator iframe2 = new WebLocator().setTag("iframe").setClasses("x-htmleditor-iframe");
        WebDriverConfig.getDriver().switchTo().frame(iframe2.getWebElement());
        body.clear();
        boolean set = body.sendKeys(value) != null;
        WebDriverConfig.getDriver().switchTo().defaultContent();
        return set;
    }

    public String getValue() {
        WebDriverConfig.getDriver().switchTo().frame(iframe.getWebElement());
        String text = body.getText();
        WebDriverConfig.getDriver().switchTo().defaultContent();
        return text;
    }
}
