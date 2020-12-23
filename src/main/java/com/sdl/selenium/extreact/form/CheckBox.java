package com.sdl.selenium.extreact.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.Field;
import com.sdl.selenium.web.form.ICheck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckBox extends Field implements ICheck {

    public CheckBox() {
        setClassName("CheckBox");
        setType("checkbox");
        setTag("input");
        setLabelPosition("/..//");
    }

    public CheckBox(WebLocator container) {
        this();
        setContainer(container);
    }

    public CheckBox(WebLocator container, String label, SearchType... searchTypes) {
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

    public CheckBox(String boxLabel, WebLocator container, SearchType... searchTypes) {
        this(container);
        setLabel(boxLabel, searchTypes);
    }

    @Deprecated
    public boolean isSelected() {
        return isChecked();
    }

    @Override
    public boolean isChecked() {
        return executor.isSelected(this);
    }
}