package com.sdl.selenium.web.button;

import com.sdl.selenium.web.Clickable;
import com.sdl.selenium.web.IWebLocator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface IButton extends IWebLocator, Clickable {
    Logger log = LogManager.getLogger(IButton.class);

    default void logIfButtonIsDisabled() {
        if (!isEnabled()) {
            log.warn("Button '" + this + "' is disabled!");
        }
    }
}