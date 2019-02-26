package com.sdl.selenium.web;

public interface Editable {

    boolean clear();

    boolean doClear();

    void sendKeys(CharSequence... charSequences);

    boolean doSendKeys(CharSequence... charSequences);
}