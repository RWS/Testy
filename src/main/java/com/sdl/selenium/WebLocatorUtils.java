package com.sdl.selenium;

import com.sdl.selenium.web.WebLocator;

/**
 * @author nmatei
 * @since 2/25/14
 */
public final class WebLocatorUtils extends WebLocator {
    private WebLocatorUtils(){

    }

    public static String getPageHtmlSource() {
        return executor.getHtmlSource();
    }

    public static Object doExecuteScript(String script, Object... objects) {
        return executor.executeScript(script, objects);
    }
}
