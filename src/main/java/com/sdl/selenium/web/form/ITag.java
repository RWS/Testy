package com.sdl.selenium.web.form;

import java.util.List;

public interface ITag {

    boolean select(String... value);

    default boolean select(List<String> values) {
        return select(values.toArray(new String[0]));
    }

    default boolean remove(List<String> values) {
        return remove(values.toArray(new String[0]));
    }

    boolean remove(String... values);

    List<String> getAllSelectedValues();
}