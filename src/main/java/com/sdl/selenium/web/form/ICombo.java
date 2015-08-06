package com.sdl.selenium.web.form;

import com.sdl.selenium.web.Editable;
import com.sdl.selenium.web.IWebLocator;

public interface ICombo extends IWebLocator, Editable {

    boolean select(String value);

    String getValue();

}
