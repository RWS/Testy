package com.sdl.selenium.web;

import com.sdl.selenium.utils.config.WebLocatorConfig;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface WebLocatorExecutor {
    boolean highlight = WebLocatorConfig.isHighlight();

    boolean doClick(WebLocator el);

    boolean doClickAt(WebLocator el);

    boolean doubleClickAt(WebLocator el);

    boolean submit(WebLocator el);

    boolean clear(WebLocator el);

    void doSendKeys(WebLocator el, java.lang.CharSequence... charSequences);

    boolean setValue(WebLocator el, String value);

    String getCssValue(final WebLocator el, final String propertyName);

    String getTagName(WebLocator el);

    String getAttribute(final WebLocator el, final String attribute);

    String getAttributeId(final WebLocator el);

    String getCurrentElementAttribute(final WebLocator el, final String attribute);

    String getHtmlText(WebLocator el);

    String getHtmlSource();

    String getHtmlSource(WebLocator el);

    String getValue(WebLocator el);

    boolean isElementPresent(WebLocator el);

    WebElement findElement(WebLocator el);

    WebElement waitElement(WebLocator el, final long millis);

    int size(WebLocator el);

    Point getLocation(WebLocator el);

    Dimension getSize(WebLocator el);

    void focus(WebLocator el);

    void doMouseOver(WebLocator el);

    void blur(WebLocator el);

    boolean isTextPresent(WebLocator el, String text);

    boolean exists(WebLocator el);

    boolean isSelected(WebLocator el);

    boolean isDisplayed(WebLocator el);

    boolean isEnabled(WebLocator el);

    boolean isSamePath(WebLocator el, String path);

    Object executeScript(String script, Object... objects);

    Object fireEventWithJS(WebLocator el, String eventName);

    void doHighlight(WebLocator el);

    boolean download(String fileName);

    public boolean browse(WebLocator el);

    public boolean upload(String... filePath);

    List<WebElement> getMatchedElements(WebLocator webLocator);
}