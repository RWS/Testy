package com.sdl.selenium.web;

public interface Editable {

    boolean clear();

    boolean doClear();

    WebLocator sendKeys(CharSequence... charSequences);

    WebLocator doSendKeys(CharSequence... charSequences);
}