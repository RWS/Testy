package com.sdl.selenium.web.form;

import com.sdl.selenium.web.Editable;
import com.sdl.selenium.web.IWebLocator;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public interface IField extends IWebLocator, Editable {

    <T extends IField> T setPlaceholder(final String value, SearchType... searchTypes);

    default WebLocator getTriggerEl(WebLocator container, String icon) {
        WebLocator ancestor = new WebLocator(container).setElPath("/ancestor::*[contains(concat(' ', @class, ' '), ' x-form-trigger-wrap ')]");
        return new WebLocator(ancestor).setRoot("/").setClasses("x-form-" + icon).setInfoMessage(container + " -> " + icon);
    }
}