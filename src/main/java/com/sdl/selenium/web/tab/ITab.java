package com.sdl.selenium.web.tab;

import com.sdl.selenium.web.Clickable;
import com.sdl.selenium.web.IWebLocator;

public interface ITab extends IWebLocator, Clickable {

    boolean setActive();

    boolean isActive();

}
