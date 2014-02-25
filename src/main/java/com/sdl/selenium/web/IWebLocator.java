package com.sdl.selenium.web;

/**
 * @author nmatei
 * @since 2/25/14
 */
public interface IWebLocator {
    String getHtmlText();

    boolean isElementPresent();

    int size();

    boolean isVisible();

    boolean waitToRender();

    boolean waitToRender(int seconds);

    boolean waitToRender(final long millis);

    boolean ready();

    boolean ready(int seconds);
}
