package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.WebLocator;

public class CheckBoxCell extends CheckBox {

    public CheckBoxCell() {
        setClassName("CheckBoxCell");
        setTag("span");
        setBaseCls("x-grid-checkcolumn");
        setType("");
    }

    public CheckBoxCell(WebLocator container) {
        this();
        setContainer(container);
    }

    @Override
    public boolean isChecked() {
        String aClass = getAttribute("class", true);
        return aClass != null && aClass.contains("x-grid-checkcolumn-checked");
    }
}