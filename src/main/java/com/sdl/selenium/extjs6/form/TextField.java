package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import lombok.extern.slf4j.Slf4j;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Slf4j
public class TextField extends com.sdl.selenium.web.form.TextField {

    public TextField() {
        setLabelPosition("//following-sibling::*//");
        setBaseCls("x-form-text");
    }

    public TextField(Locator container) {
        this();
        setContainer(container);
    }

    public TextField(Locator container, String label) {
        this(container);
        setLabel(label, SearchType.DEEP_CHILD_NODE);
    }

    // methods
    public boolean assertSetValue(String value) {
        boolean setted = setValue(value);
        assertThat(setted, is(true));
        return true;
    }

    public WebLocator getTriggerEl(String icon) {
        return new WebLocator(this).setRoot("/").setTag("parent::*/parent::*/*").setClasses("x-form-" + icon).setInfoMessage(this + " -> " + icon);
    }

    public boolean clickIcon(String icon) {
        if (ready()) {
            WebLocator iconLocator = getTriggerEl(icon);
            iconLocator.setRenderMillis(500);
            return iconLocator.click();
        } else {
            log.warn("clickIcon : field is not ready for use: " + this);
        }
        return false;
    }

    public boolean isEnabled() {
        return !"true".equals(getAttribute("disabled"));
    }

    public String getError() {
        WebLocator error = new WebLocator(this).setRoot("/../../../../").setClasses("x-form-error-wrap");
        return error.getText();
    }
}