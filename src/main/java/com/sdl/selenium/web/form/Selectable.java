package com.sdl.selenium.web.form;

import com.sdl.selenium.web.WebLocator;

import java.util.List;

public interface Selectable extends IField {

    default WebLocator getBoundList() {
        return new WebLocator("x-boundlist").setExcludeClasses("x-masked").setVisibility(true);
    }

    List<String> getAllValues();

    boolean expand();

    default boolean collapse() {
        return expand();
    }
}