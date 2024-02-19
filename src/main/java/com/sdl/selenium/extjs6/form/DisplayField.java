package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisplayField extends WebLocator {

    public DisplayField() {
        setClassName("DisplayField");
        setBaseCls("x-form-display-field");
        setTag("div");
    }

    public DisplayField(WebLocator container) {
        this();
        setContainer(container);
    }

    public DisplayField(WebLocator container, String label, SearchType... searchTypes) {
        this(container);
        if (searchTypes.length == 0) {
            searchTypes = new SearchType[]{SearchType.DEEP_CHILD_NODE_OR_SELF};
        } else {
            List<SearchType> types = new ArrayList<>(Arrays.asList(searchTypes));
            types.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
            searchTypes = types.toArray(new SearchType[0]);
        }
        setLabel(label, searchTypes);
    }

    public String getLabel() {
        WebLocator child = new WebLocator().setTag("label");
        WebLocator parent = new WebLocator(this).setRoot("//ancestor::").setChildNodes(child);
        WebLocator labelEl = new WebLocator(parent).setRoot("/").setTag("label").setClasses("x-form-item-label");
        return labelEl.getText();
    }
}