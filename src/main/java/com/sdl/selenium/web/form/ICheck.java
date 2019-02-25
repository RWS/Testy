package com.sdl.selenium.web.form;

import com.sdl.selenium.web.Clickable;
import com.sdl.selenium.web.ILocator;

public interface ICheck extends Clickable, ILocator {

    boolean isSelected();
}