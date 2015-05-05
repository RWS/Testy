package com.sdl.selenium.web.table;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextFieldBogdan extends TextField {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextFieldBogdan.class);

    public TextFieldBogdan() {
        setLabelTag("div");
        setLabelPosition("//following::*[1]//");
        setTemplateValue("input-type", "text");
    }

    public TextFieldBogdan(WebLocator container) {
        this();
        setContainer(container);
    }

    public static void main(String[] args) {
        TextFieldBogdan textFieldBogdan = new TextFieldBogdan().setLabel("First Name:");
        LOGGER.debug("path textField {}", textFieldBogdan.getPath());

    }
}