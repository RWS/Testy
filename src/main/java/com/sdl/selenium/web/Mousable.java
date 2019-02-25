package com.sdl.selenium.web;

public interface Mousable {

    WebLocator focus();

    WebLocator doFocus();

    boolean blur();

    boolean doBlur();

    boolean mouseOver();

    boolean doMouseOver();
}