package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.IText;
import com.sdl.selenium.web.Locator;

public class UneditableInput extends Locator implements IText {

    public UneditableInput() {
        setClassName("UneditableInput");
        setBaseCls("uneditable-input");
        setTag("span");
    }

    public UneditableInput(Locator container) {
        this();
        setContainer(container);
    }

    public UneditableInput(Locator container, String label) {
        this(container);
        setLabel(label);
    }

    @Override
    public String getText() {
        return executor().getText(this);
    }
}