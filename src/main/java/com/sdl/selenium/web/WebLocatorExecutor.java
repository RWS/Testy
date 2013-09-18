package com.sdl.selenium.web;

import org.openqa.selenium.WebElement;

public interface WebLocatorExecutor {
    boolean highlight = WebLocatorConfig.isHighlight();

    boolean doClick(WebLocator el);

    boolean doClickAt(WebLocator el);

    boolean isElementPresent(WebLocator el);

    WebElement findElement(WebLocator el);

    int size(WebLocator el);

    void doHighlight(WebLocator el);

    void focus(WebLocator el);

    String getAttribute(final WebLocator el, final String attribute);

    String getCurrentElementAttribute(final WebLocator el, final String attribute);

    String getHtmlText(WebLocator el);

    String getHtmlSource(WebLocator el);

    boolean clear(WebLocator el);

    boolean doubleClickAt(WebLocator el);

    boolean doMouseOver(WebLocator el);

    boolean isSamePath(WebLocator el, String path);

    Object executeScript(String script, Object... objects);

    void type(WebLocator el, String text);

    void typeKeys(WebLocator el, String text);

    void sendKeys(WebLocator el, java.lang.CharSequence... charSequences);

    boolean isTextPresent(WebLocator el, String text);

    boolean exists(WebLocator el);

    boolean isSelected(WebLocator el);

    void blur(WebLocator el);
}