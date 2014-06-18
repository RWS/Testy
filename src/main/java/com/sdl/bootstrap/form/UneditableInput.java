package com.sdl.bootstrap.form;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.IText;
import com.sdl.selenium.web.PathBuilder;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class UneditableInput extends WebLocator implements IText {
    private static final Logger LOGGER = Logger.getLogger(UneditableInput.class);

    public UneditableInput(PathBuilder pathBuilder) {
        super(pathBuilder);
        getPathBuilder().defaults(By.className("UneditableInput"), By.baseCls("uneditable-input"), By.tag("span"));
    }

    public UneditableInput(By... bys) {
        this(new PathBuilder(bys));
    }

    public UneditableInput(WebLocator container, By... bys) {
        this(bys);
        getPathBuilder().setContainer(container);
    }

    public UneditableInput(WebLocator container, String label) {
        this(container, By.label(label));
    }
}