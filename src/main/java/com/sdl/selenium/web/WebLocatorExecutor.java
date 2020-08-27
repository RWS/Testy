package com.sdl.selenium.web;

import com.sdl.selenium.utils.config.WebLocatorConfig;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

public interface WebLocatorExecutor {

    boolean click(IWebLocator el);

    boolean clickAt(IWebLocator el);

    boolean doubleClickAt(IWebLocator el);

    boolean submit(IWebLocator el);

    boolean clear(IWebLocator el);

    boolean sendKeys(IWebLocator el, java.lang.CharSequence... charSequences);

    boolean setValue(IWebLocator el, String value);

    String getCssValue(final IWebLocator el, final String propertyName);

    String getTagName(IWebLocator el);

    String getAttribute(final IWebLocator el, final String attribute);

    String getAttribute(final IWebLocator el, final String attribute, boolean instant);

    String getAttributeId(final IWebLocator el);

    String getText(IWebLocator el);

    String getValue(IWebLocator el);

    @Deprecated
    boolean isElementPresent(IWebLocator el);

    boolean isPresent(IWebLocator el);

    WebElement findElement(IWebLocator el);

    List<WebElement> findElements(IWebLocator webLocator);

    @Deprecated
    WebElement waitElement(IWebLocator el, final long millis, boolean showXPathLog);

    WebElement waitElement(IWebLocator el, Duration duration, boolean showXPathLog);

    int size(IWebLocator el);

    Point getLocation(IWebLocator el);

    Dimension getSize(IWebLocator el);

    Rectangle getRect(IWebLocator el);

    boolean focus(IWebLocator el);

    boolean mouseOver(IWebLocator el);

    boolean blur(IWebLocator el);

    boolean isSelected(IWebLocator el);

    boolean isDisplayed(IWebLocator el);

    boolean isEnabled(IWebLocator el);

    boolean isSamePath(IWebLocator el, String path);

    Object executeScript(String script, Object... objects);

    Object fireEventWithJS(IWebLocator el, String eventName);

    void highlight(IWebLocator el);

    boolean highlight = WebLocatorConfig.isHighlight();

    boolean download(String fileName, long timeoutMillis);

    boolean browse(IWebLocator el);

    boolean upload(String filePath);
}