package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.XPathBuilder;

public class FieldContainer extends WebLocator {

    public FieldContainer() {
        setClassName("FieldContainer");
        setBaseCls("x-form-fieldcontainer");
        setTag("div");
    }

    public FieldContainer(Locator container) {
        this();
        setContainer(container);
    }

    public FieldContainer(Locator container, String label, SearchType... searchTypes) {
        this(container);
        setLabel(label, searchTypes);
    }

    public XPathBuilder createXPathBuilder() {
        return new XPathBuilder() {
            @Override
            protected String afterItemPathCreated(String itemPath) {
                if (hasLabel()) {
                    itemPath = itemPath + getLabelPath() + getLabelPosition().substring(0, getLabelPosition().lastIndexOf("//"));
                }
                itemPath = addPositionToPath(itemPath);
                return itemPath;
            }
        };
    }
}