package com.extjs.selenium.button;

import com.sdl.selenium.extjs3.ExtJsComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @deprecated pachage "com.extjs.selenium*" is deprecated, please use new package "com.sdl.selenium.extjs3"
 */
public class SplitButton extends com.sdl.selenium.extjs3.button.SplitButton {
    private static final Logger LOGGER = LoggerFactory.getLogger(Button.class);

    public SplitButton() {
        super();
    }

    public SplitButton(ExtJsComponent container) {
        super(container);
    }

    public SplitButton(ExtJsComponent container, String text) {
        super(container, text);
    }
}
