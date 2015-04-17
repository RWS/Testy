package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebLocator;

public class TextArea extends com.sdl.selenium.web.form.TextArea {

    public TextArea(By ...bys) {
        getPathBuilder().defaults(By.tag("textarea")).init(bys);
    }

    public TextArea(WebLocator container) {
        this(By.container(container));
        getPathBuilder().setContainer(container);
    }

    public TextArea(WebLocator container, String label) {
        this(By.container(container), By.label(label));
    }
}