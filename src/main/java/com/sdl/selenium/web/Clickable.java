package com.sdl.selenium.web;

/**
 * @author nmatei
 * @since 2/25/14
 */
public interface Clickable {

    boolean clickAt();

    boolean assertClickAt();

    boolean click();

    boolean assertClick();

    boolean doubleClickAt();
}
