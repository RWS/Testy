package com.sdl.selenium.materialui.checkbox;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICheck;
import com.sdl.selenium.web.form.IField;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Checkbox extends WebLocator implements IField, ICheck {

    public Checkbox() {
        setClassName("Checkbox");
        setType("checkbox");
        setTag("input");
        setLabelTag("span");
        setLabelPosition("/preceding-sibling::*//");
    }

    public Checkbox(WebLocator container) {
        this();
        setContainer(container);
    }

    public Checkbox(WebLocator container, String label, SearchType... searchTypes) {
        this(container);
        if (searchTypes.length == 0) {
            searchTypes = new SearchType[]{SearchType.EQUALS};
        }
        setLabel(label, searchTypes);
    }

    @Override
    public boolean isChecked() {
        return ready() && executor.isSelected(this);
    }
}
