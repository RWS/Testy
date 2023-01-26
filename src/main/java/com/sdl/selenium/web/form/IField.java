package com.sdl.selenium.web.form;

import com.sdl.selenium.web.Editable;
import com.sdl.selenium.web.IWebLocator;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public interface IField extends IWebLocator, Editable {

    /**
     * @param value       value
     * @param searchTypes accept only SearchType.EQUALS, SearchType.CONTAINS, SearchType.STARTS_WITH, SearchType.TRIM
     * @return current element
     */
    default <T extends IField> T setPlaceholder(String value, SearchType... searchTypes) {
        setAttribute("placeholder", value, searchTypes);
        return (T) this;
    }

    default WebLocator getTriggerEl(WebLocator container, String icon) {
        WebLocator ancestor = new WebLocator(container).setElPath("/ancestor::*[contains(concat(' ', @class, ' '), ' x-form-trigger-wrap ')]");
        return new WebLocator(ancestor).setRoot("/").setClasses("x-form-" + icon).setInfoMessage(container + " -> " + icon);
    }
}