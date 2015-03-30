package com.sdl.bootstrap.window;

/**
 * @deprecated package "com.sdl.bootstrap.*" is deprecated, please use new package "com.sdl.selenium.bootstrap.*"
 */
public class PopOver extends com.sdl.selenium.bootstrap.window.PopOver {
    public PopOver(String title, String message) {
        super(title, message);
    }

    public PopOver(String title) {
        super(title);
    }
}