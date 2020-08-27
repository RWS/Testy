package com.sdl.selenium.web;

import org.openqa.selenium.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public interface IWebLocator {

    <T extends WebLocatorAbstractBuilder> T getLocator();

    default WebLocatorExecutor getExecutor() {
        return Hidden.getExecutor();
    }

    public static void setDriverExecutor(WebDriver driver) {
        Hidden.setDriverExecutor(driver);
    }

    class Hidden {
        private static WebLocatorDriverExecutor executor;
        private static String currentElementPath;
        private static WebElement currentElement;

        public static void setDriverExecutor(WebDriver webDriver) {
            Hidden.executor = new WebLocatorDriverExecutor(webDriver);
        }

        public static WebLocatorDriverExecutor getExecutor() {
            return executor;
        }

        private static void setCurrentElementPath(String currentElementPath) {
            Hidden.currentElementPath = currentElementPath;
        }

        public static String getCurrentElementPath() {
            return currentElementPath;
        }

        public static WebElement getCurrentElement() {
            return currentElement;
        }

        public static void setCurrentElement(WebElement currentElement) {
            Hidden.currentElement = currentElement;
        }
    }

    default String getCssValue(String propertyName) {
        return getExecutor().getCssValue(getLocator(), propertyName);
    }

    default String getAttributeId() {
        return getExecutor().getAttributeId(getLocator());
    }

    default String getAttributeClass() {
        return getExecutor().getAttribute(getLocator(), "class");
    }

    default String getCurrentElementPath() {
        return Hidden.getCurrentElementPath();
    }

    default void setCurrentElementPath(String currentElementPath) {
        Hidden.setCurrentElementPath(currentElementPath);
    }

    default WebElement getWebElement() {
        return Hidden.getCurrentElement() == null ? findElement() : Hidden.getCurrentElement();
    }

    /**
     //     * @return The tag name of this element.
     //     */
    default String getTagName() {
        return getExecutor().getTagName(getLocator());
    }

    /**
     * @param attribute e.g. class, id
     * @return String attribute, if element not exist return null.
     */
    default String getAttribute(String attribute) {
        return getExecutor().getAttribute(getLocator(), attribute);
    }

    @Deprecated
    default boolean isElementPresent() {
        return isPresent();
    }

    default boolean isPresent() {
        return getExecutor().isPresent(getLocator());
    }

    default int size() {
        return getExecutor().size(getLocator());
    }

    default Point getLocation() {
        return getExecutor().getLocation(getLocator());
    }

    default Dimension getSize() {
        return getExecutor().getSize(getLocator());
    }

    default Rectangle getRect() {
        return getExecutor().getRect(getLocator());
    }

    default WebElement findElement() {
        return getExecutor().findElement(getLocator());
    }

    default List<WebElement> findElements() {
        boolean findElements = waitToRender();
        assertThat("Elements were not rendered " + toString(), findElements);
        return getExecutor().findElements(getLocator());
    }

    default List<WebElement> doFindElements() {
        return getExecutor().findElements(getLocator());
    }

    @Deprecated
    default boolean isVisible() {
        boolean visible = isPresent();
        if (visible) {
            String style = getAttribute("style");
            style = style == null ? "" : style.toLowerCase();
            style = style.replaceAll("\\s*:\\s*", ":");
            if (style.contains("visibility:hidden") || style.contains("display:none")) {
                visible = false;
            }
            /*else {
                visible = getContainer().isVisible();
                //TODO if must check parent is visible
            }*/
        }
        return visible;
    }

    boolean waitToRender();

    @Deprecated
    default boolean waitToRender(final long millis) {
        return waitToRender(Duration.ofMillis(millis));
    }

    default boolean waitToRender(Duration duration) {
        return getExecutor().waitElement(getLocator(), duration, true) != null;
    }

    @Deprecated
    default boolean waitToRender(final long millis, boolean showXPathLog) {
        return waitToRender(Duration.ofMillis(millis), showXPathLog);
    }

    default boolean waitToRender(Duration duration, boolean showXPathLog) {
        return getExecutor().waitElement(getLocator(), duration, showXPathLog) != null;
    }

    boolean waitToActivate();

    default boolean waitToActivate(Duration duration) {
        return true;
    }

    default boolean ready() {
        return waitToRender() && waitToActivate();
    }

    default boolean assertReady(String... values) {
        boolean ready = ready();
        if (values.length == 0) {
            assertThat("Element is not ready: '" + getLocator() + "'", ready);
        } else {
            assertThat("Element is not ready: '" + getLocator() + "' for values: " + Arrays.toString(values), ready);
        }
        return ready;
    }

    @Deprecated
    default boolean ready(int seconds) {
        return ready(Duration.ofSeconds(seconds));
    }

    default boolean ready(Duration duration) {
        return waitToRender(duration) && waitToActivate(duration);
    }

    default boolean isEnabled() {
        return getExecutor().isEnabled(getLocator());
    }

    default boolean isDisplayed() {
        return getExecutor().isDisplayed(getLocator());
    }

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