package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.IText;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UneditableInput extends WebLocator implements IText {
    private static final Logger LOGGER = LoggerFactory.getLogger(UneditableInput.class);

    public UneditableInput(By ...bys) {
        getPathBuilder().defaults(By.baseCls("uneditable-input"), By.tag("span")).init(bys);
    }

    public UneditableInput(WebLocator container) {
        this(By.container(container));
    }

    public UneditableInput(WebLocator container, String label) {
        this(By.container(container), By.label(label));
    }
}