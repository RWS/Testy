package com.sdl.selenium;

public enum Go {
    START("start"),
    CENTER("center"),
    END("end"),
    NEAREST("nearest");

    private final String value;

    Go(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
