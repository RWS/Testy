package com.sdl.selenium.web;

public enum Browser {
    IEXPLORE("ie"),

    FIREFOX("firefox"),

    CHROME("chrome"),

    HTMLUNIT("htmlunit");

    private String driverKey;

    Browser(String driverKey){
        this.driverKey = driverKey;
    }

    public String getDriverKey() {
        return driverKey;
    }
}

