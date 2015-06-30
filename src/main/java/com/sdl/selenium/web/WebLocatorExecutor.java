package com.sdl.selenium.web;

import com.sdl.selenium.utils.config.WebLocatorConfig;
import org.openqa.selenium.WebElement;

public interface WebLocatorExecutor {
    boolean highlight = WebLocatorConfig.isHighlight();

    boolean doClick(WebLocator el);

    boolean doClickAt(WebLocator el);

    boolean isElementPresent(WebLocator el);

    WebElement findElement(WebLocator el);

    WebElement waitElement(WebLocator el, final long millis);

    int size(WebLocator el);

    void doHighlight(WebLocator el);

    void focus(WebLocator el);

    String getAttribute(final WebLocator el, final String attribute);

    String getCurrentElementAttribute(final WebLocator el, final String attribute);

    String getHtmlText(WebLocator el);

    String getHtmlSource();

    String getHtmlSource(WebLocator el);

    boolean setValue(WebLocator el, String value);

    String getValue(WebLocator el);

    boolean clear(WebLocator el);

    boolean doubleClickAt(WebLocator el);

    void doMouseOver(WebLocator el);

    boolean isSamePath(WebLocator el, String path);

    Object executeScript(String script, Object... objects);

    void doSendKeys(WebLocator el, java.lang.CharSequence... charSequences);

    boolean isTextPresent(WebLocator el, String text);

    boolean exists(WebLocator el);

    boolean isSelected(WebLocator el);

    boolean isDisplayed(WebLocator el);

    boolean isEnabled(WebLocator el);

    void blur(WebLocator el);

    boolean submit(WebLocator el);
}