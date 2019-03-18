package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FieldContainer extends WebLocator {

    public FieldContainer() {
        setClassName("FieldContainer");
        setBaseCls("x-form-fieldcontainer");
        setTag("div");
    }

    public FieldContainer(WebLocator container) {
        this();
        setContainer(container);
    }

    public FieldContainer(WebLocator container, String label, SearchType... searchTypes) {
        this(container);
        if (searchTypes.length == 0) {
            searchTypes = new SearchType[]{SearchType.DEEP_CHILD_NODE};
        } else {
            List<SearchType> types = new ArrayList<>(Arrays.asList(searchTypes));
            types.add(SearchType.DEEP_CHILD_NODE);
            searchTypes = types.toArray(new SearchType[0]);
        }
        WebLocator labelEl = new WebLocator().setTag("label").setText(label, searchTypes);
        setChildNodes(labelEl);
    }
}