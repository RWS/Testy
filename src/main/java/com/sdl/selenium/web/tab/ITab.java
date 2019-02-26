package com.sdl.selenium.web.tab;

import com.sdl.selenium.web.Clickable;
import com.sdl.selenium.web.ILocator;

public interface ITab extends ILocator, Clickable {

    boolean setActive();

    boolean isActive();

    boolean isTabDisplayed();

    boolean close();
}