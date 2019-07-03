package com.sdl.selenium.web.form;

import java.util.List;

public interface ITag extends IField, Selectable {

    boolean select(String... value);

    boolean remove(String... values);

    List<String> getAllSelectedValues();
}