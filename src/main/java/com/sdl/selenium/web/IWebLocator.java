package com.sdl.selenium.web;

/**
 * @author nmatei
 * @since 2/25/14
 */
public interface IWebLocator {

    String getAttributeId();

    String getAttributeClass();

    String getCurrentElementPath();

    /**
     * @param attribute e.g. class, id
     * @return String attribute, if element not exist return null.
     */
    public String getAttribute(String attribute);

    boolean isElementPresent();

    int size();

    boolean isVisible();

    boolean waitToRender();

    boolean waitToRender(final long millis);

    boolean ready();

    boolean ready(int seconds);
}
