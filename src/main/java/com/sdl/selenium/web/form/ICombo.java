package com.sdl.selenium.web.form;

public interface ICombo extends IField, Selectable {

    boolean select(String value);

    String getValue();
}