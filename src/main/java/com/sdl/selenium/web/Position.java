package com.sdl.selenium.web;

/**
 * Contains all Position:
 * see details for all in:
 * <ul>
 * <li>{@link Position#FIRST}</li>
 * <li>{@link Position#LAST}</li>
 * </ul>
 */
public enum Position {

    /**
     * WebLocator cancelBtn = new WebLocator().setPosition(Position.FIRST);
     */
    FIRST("first()"),

    /**
     * WebLocator cancelBtn = new WebLocator().setPosition(Position.LAST);
     */
    LAST("last()");

    private final String value;

    Position(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
