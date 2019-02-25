package com.sdl.selenium.web;

import com.sdl.selenium.utils.config.WebDriverConfig;

public interface IActions {

    default NewWebLocatorExecutor executor() {
        return new NewWebLocatorExecutor(WebDriverConfig.getDriver());
    }

    String getTagName();

    String getCssValue(String propertyName);

    String getAttributeId();

    String getAttributeClass();

    /**
     * @param attribute e.g. class, id
     * @return String attribute, if element not exist return null.
     */
    String getAttribute(String attribute);

    boolean isElementPresent();

    int size();

    boolean isEnabled();

    boolean waitToRender();

    boolean waitToRender(final long millis);

    boolean waitToRender(final long millis, boolean showXPathLog);

    boolean ready();

    boolean ready(int seconds);
}