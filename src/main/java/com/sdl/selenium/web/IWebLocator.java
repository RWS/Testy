package com.sdl.selenium.web;

import org.openqa.selenium.*;

import java.time.Duration;
import java.util.List;

public interface IWebLocator {

    default WebLocatorExecutor getExecutor() {
        return Hidden.getExecutor();
    }

    public static void setDriverExecutor(WebDriver driver) {
        Hidden.setExecutor(driver);
    }

    class Hidden {
        static WebLocatorDriverExecutor executor;
        private static void setExecutor(WebDriver webDriver) {
            executor = new WebLocatorDriverExecutor(webDriver);
        }

        public static WebLocatorDriverExecutor getExecutor(){
            return executor;
        }
    }

    String getCssValue(String propertyName);

    String getAttributeId();

    String getAttributeClass();

    String getCurrentElementPath();

    /**
     * @param attribute e.g. class, id
     * @return String attribute, if element not exist return null.
     */
    String getAttribute(String attribute);

    @Deprecated
    boolean isElementPresent();

    boolean isPresent();

    int size();

    Point getLocation();

    Dimension getSize();

    Rectangle getRect();

    WebElement findElement();

    List<WebElement> findElements();

    @Deprecated
    boolean isVisible();

    boolean waitToRender();

    @Deprecated
    boolean waitToRender(final long millis);

    boolean waitToRender(Duration duration);

    @Deprecated
    boolean waitToRender(final long millis, boolean showXPathLog);

    boolean waitToRender(Duration duration, boolean showXPathLog);

    boolean ready();

    @Deprecated
    boolean ready(int seconds);

    boolean ready(Duration duration);

    boolean isEnabled();

    boolean isDisplayed();

    String getXPath();

    <T extends WebLocatorAbstractBuilder> T setRoot(final String root);

    <T extends WebLocatorAbstractBuilder> T setTag(final String tag);

    <T extends WebLocatorAbstractBuilder> T setId(final String id);

    <T extends WebLocatorAbstractBuilder> T setElPath(final String elPath);

    <T extends WebLocatorAbstractBuilder> T setBaseCls(final String baseCls);

    <T extends WebLocatorAbstractBuilder> T setCls(final String cls);

    <T extends WebLocatorAbstractBuilder> T setClasses(final String... classes);

    <T extends WebLocatorAbstractBuilder> T setExcludeClasses(final String... excludeClasses);

    <T extends WebLocatorAbstractBuilder> T setChildNodes(final WebLocator... childNodes);

    <T extends WebLocatorAbstractBuilder> T setName(final String name);

    <T extends WebLocatorAbstractBuilder> T setLocalName(final String localName);

    <T extends WebLocatorAbstractBuilder> T setText(final String text, final SearchType... searchTypes);

    <T extends WebLocatorAbstractBuilder> T setSearchTextType(SearchType... searchTextTypes);

    <T extends WebLocatorAbstractBuilder> T addSearchTextType(SearchType... searchTextTypes);

//    <T extends WebLocatorAbstractBuilder> T setSearchLabelType(SearchType... searchLabelType);

    <T extends WebLocatorAbstractBuilder> T setStyle(final String style);

    <T extends WebLocatorAbstractBuilder> T setTitle(final String title, SearchType... searchTypes);

    <T extends WebLocatorAbstractBuilder> T setTemplateTitle(WebLocator titleEl);

    <T extends WebLocatorAbstractBuilder> T setElPathSuffix(final String key, final String elPathSuffix);

    <T extends WebLocatorAbstractBuilder> T setTemplateValue(final String key, final String... value);

    <T extends WebLocatorAbstractBuilder> T setTemplate(final String key, final String value);

    <T extends WebLocatorAbstractBuilder> T addToTemplate(final String key, final String value);

    <T extends WebLocatorAbstractBuilder> T setInfoMessage(final String infoMessage);

    <T extends WebLocatorAbstractBuilder> T setVisibility(final boolean visibility);

    @Deprecated
    <T extends WebLocatorAbstractBuilder> T setRenderMillis(final long renderMillis);

    <T extends WebLocatorAbstractBuilder> T setRender(Duration duration);

    @Deprecated
    <T extends WebLocatorAbstractBuilder> T setActivateSeconds(final int activateSeconds);

    <T extends WebLocatorAbstractBuilder> T setActivate(Duration duration);

    <T extends WebLocatorAbstractBuilder> T setContainer(WebLocator container);

    <T extends WebLocatorAbstractBuilder> T setLabel(final String label, final SearchType... searchTypes);

    <T extends WebLocatorAbstractBuilder> T setLabelTag(final String labelTag);

    <T extends WebLocatorAbstractBuilder> T setLabelPosition(final String labelPosition);

    <T extends WebLocatorAbstractBuilder> T setPosition(final int position);

    <T extends WebLocatorAbstractBuilder> T setResultIdx(final int resultIdx);

    <T extends WebLocatorAbstractBuilder> T setType(final String type);

    <T extends WebLocatorAbstractBuilder> T setAttribute(final String attribute, final String value, final SearchType... searchTypes);
}