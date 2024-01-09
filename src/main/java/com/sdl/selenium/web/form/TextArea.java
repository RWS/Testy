package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.WebElement;

public class TextArea extends TextField {

    public TextArea() {
        setClassName("TextArea");
        setTag("textarea");
    }

    public TextArea(WebLocator container) {
        this();
        setContainer(container);
    }

    public TextArea(WebElement webElement) {
        this();
        setWebElement(webElement);
    }

    public TextArea(WebLocator container, String label) {
        this(container);
        setLabel(label);
    }
}