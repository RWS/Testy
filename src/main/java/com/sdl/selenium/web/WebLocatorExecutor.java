package com.sdl.selenium.web;

import com.sdl.selenium.utils.config.WebLocatorConfig;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

public interface WebLocatorExecutor {

    boolean click(WebLocatorOld el);

    boolean clickAt(WebLocatorOld el);

    boolean doubleClickAt(WebLocatorOld el);

    boolean submit(WebLocatorOld el);

    boolean clear(WebLocatorOld el);

    boolean sendKeys(WebLocatorOld el, java.lang.CharSequence... charSequences);

    boolean setValue(WebLocatorOld el, String value);

    String getCssValue(final WebLocatorOld el, final String propertyName);

    String getTagName(WebLocatorOld el);

    String getAttribute(final WebLocatorOld el, final String attribute);

    String getAttributeId(final WebLocatorOld el);

    String getCurrentElementAttribute(final WebLocatorOld el, final String attribute);

    String getText(WebLocatorOld el);

    String getValue(WebLocatorOld el);

    boolean isElementPresent(WebLocatorOld el);

    WebElement findElement(WebLocatorOld el);

    List<WebElement> findElements(WebLocatorOld WebLocatorOld);

    @Deprecated
    WebElement waitElement(WebLocatorOld el, final long millis, boolean showXPathLog);

    WebElement waitElement(WebLocatorOld el, Duration duration, boolean showXPathLog);

    int size(WebLocatorOld el);

    Point getLocation(WebLocatorOld el);

    Dimension getSize(WebLocatorOld el);

    Rectangle getRect(WebLocatorOld el);

    boolean focus(WebLocatorOld el);

    boolean mouseOver(WebLocatorOld el);

    boolean blur(WebLocatorOld el);

    boolean isSelected(WebLocatorOld el);

    boolean isDisplayed(WebLocatorOld el);

    boolean isEnabled(WebLocatorOld el);

    boolean isSamePath(WebLocatorOld el, String path);

    Object executeScript(String script, Object... objects);

    Object fireEventWithJS(WebLocatorOld el, String eventName);

    void highlight(WebLocatorOld el);

    boolean highlight = WebLocatorConfig.isHighlight();

    boolean download(String fileName, long timeoutMillis);

    boolean browse(WebLocatorOld el);

    boolean upload(String filePath);
}