package com.sdl.selenium.web;

import com.sdl.selenium.utils.config.WebLocatorConfig;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

public interface WebLocatorExecutor {

    boolean click(WebLocator el);

    boolean clickAt(WebLocator el);

    boolean doubleClickAt(WebLocator el);

    boolean submit(WebLocator el);

    boolean clear(WebLocator el);

    boolean sendKeys(WebLocator el, java.lang.CharSequence... charSequences);

    boolean setValue(WebLocator el, String value);

    String getCssValue(final WebLocator el, final String propertyName);

    String getTagName(WebLocator el);

    String getAttribute(final WebLocator el, final String attribute);

    String getAttribute(final WebLocator el, final String attribute, boolean instant);

    String getAttributeId(final WebLocator el);

    String getText(WebLocator el);

    String getText(WebLocator el, boolean instant);

    String getValue(WebLocator el);

    String getValue(WebLocator el, boolean instant);

    @Deprecated
    boolean isElementPresent(WebLocator el);

    boolean isPresent(WebLocator el);

    WebElement findElement(WebLocator el);

    List<WebElement> findElements(WebLocator webLocator);

    @Deprecated
    WebElement waitElement(WebLocator el, final long millis, boolean showXPathLog);

    WebElement waitElement(WebLocator el, Duration duration, boolean showXPathLog);

    int size(WebLocator el);

    Point getLocation(WebLocator el);

    Dimension getSize(WebLocator el);

    Rectangle getRect(WebLocator el);

    boolean focus(WebLocator el);

    boolean mouseOver(WebLocator el);

    boolean blur(WebLocator el);

    boolean isSelected(WebLocator el);

    boolean isDisplayed(WebLocator el);

    boolean isEnabled(WebLocator el);

    boolean isSamePath(WebLocator el, String path);

    Object executeScript(String script, Object... objects);

    Object fireEventWithJS(WebLocator el, String eventName);

    void highlight(WebLocator el);

    boolean highlight = WebLocatorConfig.isHighlight();

    boolean download(String fileName, long timeoutMillis);

    boolean browse(WebLocator el);

    boolean upload(String filePath);
}