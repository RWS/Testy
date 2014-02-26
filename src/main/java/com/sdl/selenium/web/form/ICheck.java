package com.sdl.selenium.web.form;

import com.sdl.selenium.web.Clickable;
import com.sdl.selenium.web.IWebLocator;

public interface ICheck extends Clickable, IWebLocator {

    boolean isSelected();

}
