package com.sdl.selenium.web.form;

import com.sdl.selenium.web.Editable;
import com.sdl.selenium.web.ILocator;

public interface ICombo extends ILocator, Editable {

    boolean select(String value);

    String getValue();

    boolean expand();

    boolean collapse();
}