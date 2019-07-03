package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebLocator;

import java.util.List;

public interface Selectable extends IField {

    default WebLocator getBoundList() {
        return new WebLocator("x-boundlist").setVisibility(true);
    }

    List<String> getAllValues();

    boolean expand();

    default WebLocator getTriggerEl(WebLocator container, String icon) {
        WebLocator ancestor = new WebLocator(container).setElPath("/ancestor::*[contains(concat(' ', @class, ' '), ' x-form-trigger-wrap ')]");
        return new WebLocator(ancestor).setRoot("/").setClasses("x-form-" + icon).setInfoMessage(container + " -> " + icon);
    }

    default boolean collapse() {
        return expand();
    }
}