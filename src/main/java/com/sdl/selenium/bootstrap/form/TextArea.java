package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextArea extends com.sdl.selenium.web.form.TextArea {

    public TextArea(By ...bys) {
        getPathBuilder().init(bys);
        getPathBuilder().defaults(By.tag("textarea"));
    }

    public TextArea(WebLocator container, By ...bys) {
        this(bys);
        getPathBuilder().setContainer(container);
    }

    public TextArea(WebLocator container, String label) {
        this(container, By.label(label));
    }
}