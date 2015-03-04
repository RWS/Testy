package com.extjs.selenium.window;

/**
 * @deprecated pachage "com.extjs.selenium*" is deprecated, please use new package "com.sdl.selenium.extjs3"
 */
public class Window extends com.sdl.selenium.extjs3.window.Window {
    public Window() {
    }

    public Window(Boolean modal) {
        super(modal);
    }

    public Window(String title) {
        super(title);
    }

    public Window(String title, Boolean modal) {
        super(title, modal);
    }
}