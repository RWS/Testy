package com.sdl.selenium.web;

public interface Editable {

    WebLocator sendKeys(java.lang.CharSequence... charSequences);

    boolean clear();

}
