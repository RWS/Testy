package com.sdl.selenium.web;

/**
 * @author nmatei
 * @since 3/7/14
 */
public enum Browser {
    IEXPLORE("ie", "*iexplore", "iexplore"),

    FIREFOX("firefox", "*firefox", "firefox"),

    CHROME("chrome", "*chrome"),

    HTMLUNIT("htmlunit", "*htmlunit");

    private String driverKey;
    private String[] names;

    Browser(String driverKey, String... names){
        this.driverKey = driverKey;
        this.names = names;
    }

    public String getDriverKey() {
        return driverKey;
    }
}
