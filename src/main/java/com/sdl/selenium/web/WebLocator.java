package com.sdl.selenium.web;

import com.sdl.selenium.web.utils.Utils;
import org.openqa.selenium.*;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class WebLocator extends WebLocatorAbstractBuilder implements Cloneable, IWebLocator {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(WebLocator.class);
    private String currentElementPath = "";
    public WebElement currentElement;

    public static WebLocatorExecutor getExecutor() {
        return executor;
    }

    protected static WebLocatorExecutor executor;

    public WebLocator() {
    }

    /**
     * @param cls css class
     */
    public WebLocator(String cls) {
        setClasses(cls);
    }

    public WebLocator(WebLocator container) {
        setContainer(container);
    }

    public WebLocator(String cls, WebLocator container) {
        this(container);
        setClasses(cls);
    }

    public WebLocator(String text, String cls, WebLocator container) {
        this(cls, container);
        setText(text);
    }

    public WebLocator(String tag, String baseCls) {
        setTag(tag);
        setBaseCls(baseCls);
    }

    // getters and setters

    public static void setDriverExecutor(WebDriver driver) {
        executor = new WebLocatorDriverExecutor(driver);
    }

    /**
     * @param propertyName property name
     * @return Element value of css property
     */
    public String getCssValue(String propertyName) {
        return executor.getCssValue(this, propertyName);
    }

    /**
     * @return The tag name of this element.
     */
    public String getTagName() {
        return executor.getTagName(this);
    }

    /**
     * @return The id of this element.
     */
    public String getAttributeId() {
        return getAttribute("id");
    }

    /**
     * @return The classes of this element.
     */
    public String getAttributeClass() {
        return getAttribute("class");
    }

    public String getCurrentElementPath() {
        return currentElementPath;
    }

    public void setCurrentElementPath(String currentElementPath) {
        this.currentElementPath = currentElementPath;
    }

    /**
     * @return This method return the WebElement
     */
    public WebElement getWebElement() {
        return currentElement != null ? currentElement : findElement();
    }

    /**
     *
     * @param attribute eg "id" or "class"
     * @return String attribute, if element not exist return null.
     */
    public String getAttribute(String attribute) {
        return executor.getAttribute(this, attribute);
    }

    /**
     *
     * @param attribute eg "id" or "class"
     * @param instant true or false
     * @return String attribute, if element not exist return null.
     */
    public String getAttribute(String attribute, boolean instant) {
        return executor.getAttribute(this, attribute, instant);
    }

    /**
     * @return The text of this element.
     */
    public String getText() {
        return getText(false);
    }

    /**
     * @param instant If instant is true, return text without wait to render element.
     * @return The text of this element.
     */
    public String getText(boolean instant) {
        if (instant || ready()) {
            return executor.getText(this);
        }
        return null;
    }

    public boolean clickAt() {
        boolean clickAt = waitToRender();
        assertThat("Element was not rendered " + toString(), clickAt);
        clickAt = executor.clickAt(this);
        assertThat("Could not clickAt " + toString(), clickAt);
        log.info("clickAt on {}", toString());
        return clickAt;
    }

    /**
     * doClickAt does not make sure element is present, if you are not sure about this, please use click() instead
     *
     * @return true | false
     */
    public boolean doClickAt() {
        boolean doClick = waitToRender();
        if (doClick) {
            doClick = executor.clickAt(this);
            if (doClick) {
                log.info("clickAt on {}", toString());
            } else {
                log.info("Could not clickAt {}", toString());
            }
        }
        return doClick;
    }

    /**
     * Click once do you catch exceptions StaleElementReferenceException.
     *
     * @return true | false
     */
    public boolean click() {
        return click(true);
    }

    public boolean click(boolean showLog) {
        boolean click = waitToRender();
        assertThat("Element was not rendered " + toString(), click);
        click = executor.click(this);
        assertThat("Could not click " + toString(), click);
        if (showLog) {
            log.info("click on {}", toString());
        }
        return click;
    }

    /**
     * doClick does not make sure element is present, if you are not sure about this, please use click() instead
     *
     * @return true | false
     */
    public boolean doClick() {
        return doClick(true);
    }

    public boolean doClick(boolean showLog) {
        boolean doClick = waitToRender();
        if (doClick) {
            doClick = executor.click(this);
            if (showLog) {
                if (doClick) {
                    log.info("click on {}", toString());
                } else {
                    log.info("Could not click {}", toString());
                }
            }
        }
        return doClick;
    }

    public void highlight() {
        if (isPresent()) {
            doHighlight();
        }
    }

    private void doHighlight() {
        executor.highlight(this);
    }

    public WebLocator sendKeys(java.lang.CharSequence... charSequences) {
        return sendKeys(true, charSequences);
    }

    public WebLocator sendKeys(boolean showLog, java.lang.CharSequence... charSequences) {
        boolean sendKeys = waitToRender();
        assertThat("Element was not rendered " + toString(), sendKeys);
        sendKeys = executor.sendKeys(this, charSequences);
        assertThat("Could not sendKeys " + toString(), sendKeys);
        if (showLog) {
            log.info("sendKeys value({}): '{}'", toString(), getKeysName(charSequences));
        }
        return this;
    }

    public WebLocator doSendKeys(java.lang.CharSequence... charSequences) {
        return doSendKeys(true, charSequences);
    }

    public WebLocator doSendKeys(boolean showLog, java.lang.CharSequence... charSequences) {
        boolean doSendKeys = waitToRender();
        if (doSendKeys) {
            doSendKeys = executor.sendKeys(this, charSequences);
            if (showLog) {
                if (doSendKeys) {
                    log.info("sendKeys value({}): '{}'", toString(), getKeysName(charSequences));
                } else {
                    log.info("Could not sendKeys {}", toString());
                }
            }
        }
        return doSendKeys ? this : null;
    }

    private String getKeysName(java.lang.CharSequence... charSequences) {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (CharSequence ch : charSequences) {
            if (i > 0) {
                builder.append(",");
            }
            builder.append(ch instanceof Keys ? ((Keys) ch).name() : ch);
            i++;
        }
        return builder.toString();
    }

    public boolean clear() {
        boolean clear = waitToRender();
        assertThat("Element was not rendered " + toString(), clear);
        clear = executor.clear(this);
        assertThat("Could not clear " + toString(), clear);
        log.info("clear on {}", toString());
        return clear;
    }

    public boolean doClear() {
        boolean doClear = waitToRender();
        if (doClear) {
            doClear = executor.clear(this);
            if (doClear) {
                log.info("clear on {}", toString());
            } else {
                log.info("Could not clear {}", toString());
            }
        }
        return doClear;
    }

    public boolean mouseOver() {
        boolean mouseOver = waitToRender();
        assertThat("Element was not rendered " + toString(), mouseOver);
        mouseOver = executor.mouseOver(this);
        assertThat("Could not mouse over " + toString(), mouseOver);
        log.info("mouse over on {}", toString());
        return mouseOver;
    }

    public boolean doMouseOver() {
        boolean doMouseOver = waitToRender();
        if (doMouseOver) {
            doMouseOver = executor.mouseOver(this);
            if (doMouseOver) {
                log.info("mouse over on {}", toString());
            } else {
                log.info("Could not mouse over {}", toString());
            }
        }
        return doMouseOver;
    }

    public boolean blur() {
        boolean blur = waitToRender();
        assertThat("Element was not rendered " + toString(), blur);
        blur = executor.blur(this);
        assertThat("Could not blur " + toString(), blur);
        log.info("blur on {}", toString());
        return blur;
    }

    public boolean doBlur() {
        boolean doBlur = waitToRender();
        if (doBlur) {
            doBlur = executor.blur(this);
            if (doBlur) {
                log.info("blur on {}", toString());
            } else {
                log.info("Could not blur {}", toString());
            }
        }
        return doBlur;
    }

    /**
     * Using XPath only
     *
     * @return true | false
     */
    public WebLocator focus() {
        boolean focus = waitToRender();
        assertThat("Element was not rendered " + toString(), focus);
        focus = executor.focus(this);
        assertThat("Could not focus " + toString(), focus);
        log.info("focus on {}", toString());
        return this;
    }

    public WebLocator doFocus() {
        boolean doFocus = waitToRender();
        if (doFocus) {
            doFocus = executor.focus(this);
            if (doFocus) {
                log.info("focus on {}", toString());
            } else {
                log.info("Could not focus {}", toString());
            }
        }
        return doFocus ? this : null;
    }

    /**
     * @return true | false
     */
    public boolean doubleClickAt() {
        boolean doubleClickAt = waitToRender();
        assertThat("Element was not rendered " + toString(), doubleClickAt);
        doubleClickAt = executor.doubleClickAt(this);
        assertThat("Could not Double ClickAt " + toString(), doubleClickAt);
        log.info("Double ClickAt on {}", toString());
        return doubleClickAt;
    }

    public boolean doDoubleClickAt() {
        boolean doClick = waitToRender();
        if (doClick) {
            doClick = executor.doubleClickAt(this);
            if (doClick) {
                log.info("Double click on {}", toString());
            } else {
                log.info("Could not double click {}", toString());
            }
        }
        return doClick;
    }

    /**
     * @return true | false
     */
    @Deprecated
    public boolean isElementPresent() {
        return executor.isElementPresent(this);
    }

    /**
     * @return true | false
     */
    public boolean isPresent() {
        return executor.isPresent(this);
    }

    /**
     * driver.findElements(xpath).size()
     *
     * @return the number of elements in this list
     */
    public int size() {
        return executor.size(this);
    }

    /**
     * @return A point, containing the location of the top left-hand corner of the element
     */
    public Point getLocation() {
        return executor.getLocation(this);
    }

    /**
     * @return The size of the element on the page.
     */
    public Dimension getSize() {
        return executor.getSize(this);
    }

    /**
     * @return The location and size of the rendered element
     */
    public Rectangle getRect() {
        return executor.getRect(this);
    }

    // TODO see where is used and if is necessary to be public
    public WebElement findElement() {
        return executor.findElement(this);
    }

    public List<WebElement> findElements() {
        boolean findElements = waitToRender();
        assertThat("Elements were not rendered " + toString(), findElements);
        return executor.findElements(this);
    }

    public List<WebElement> doFindElements() {
        return executor.findElements(this);
    }

    @Deprecated
    public boolean isVisible() {
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

    /**
     * wait 5 seconds (or specified value for renderSeconds)
     *
     * @return true | false
     */
    public boolean waitToRender() {
        return waitToRender(getPathBuilder().getRender());
    }

    @Deprecated
    public boolean waitToRender(final long millis) {
        return executor.waitElement(this, Duration.ofMillis(millis), true) != null;
    }

    public boolean waitToRender(Duration duration) {
        return executor.waitElement(this, duration, true) != null;
    }

    @Deprecated
    public boolean waitToRender(final long millis, boolean showXPathLog) {
        return executor.waitElement(this, Duration.ofMillis(millis), showXPathLog) != null;
    }

    public boolean waitToRender(Duration duration, boolean showXPathLog) {
        return executor.waitElement(this, duration, showXPathLog) != null;
    }

    /**
     * @param seconds time in seconds
     * @return String
     */
    @Deprecated
    public String waitTextToRender(int seconds) {
        return waitTextToRender(seconds, "");
    }

    /**
     * Waits for the text to be loaded by looking at the content and not take in consideration the excludeText
     * text or what ever text is given as parameter
     *
     * @param seconds     time in seconds
     * @param excludeText exclude text
     * @return string
     */
    @Deprecated
    public String waitTextToRender(int seconds, String excludeText) {
        String text = null;
        if (seconds == 0 && ((text = getText(true)) != null && text.length() > 0 && !text.equals(excludeText))) {
            return text;
        }
        for (int i = 0, count = 5 * seconds; i < count; i++) {
            text = getText(true);
            if (text != null && text.length() > 0 && !text.equals(excludeText)) {
                return text;
            }
            if (i == 0) {
                // log only fist time
                log.debug("waitTextToRender");
            }
            Utils.sleep(200);
        }
        log.warn("No text was found for Element after " + seconds + " sec; " + this);
        return excludeText.equals(text) ? null : text;
    }

    public boolean waitToActivate() {
        return waitToActivate(getPathBuilder().getActivate());
    }

    /**
     * Wait for the element to be activated when there is deactivation mask on top of it
     *
     * @param seconds time in seconds
     * @return true | false
     */
    @Deprecated
    public boolean waitToActivate(int seconds) {
        return true;
    }

    /**
     * Wait for the element to be activated when there is deactivation mask on top of it
     *
     * @param duration time
     * @return true | false
     */
    public boolean waitToActivate(Duration duration) {
        return true;
    }

    public boolean ready() {
        return waitToRender() && waitToActivate();
    }

    public boolean assertReady(String... values) {
        boolean ready = ready();
        if (values.length == 0) {
            assertThat("Element is not ready: '" + this + "'", ready);
        } else {
            assertThat("Element is not ready: '" + this + "' for values: " + Arrays.toString(values), ready);
        }
        return ready;
    }

    public boolean ready(Duration duration) {
        return waitToRender(duration, true) && waitToActivate(duration);
    }

    @Deprecated
    public boolean ready(int seconds) {
        return waitToRender(seconds * 1000) && waitToActivate(seconds);
    }

    /**
     * @return True if the element is disabled, false otherwise.
     * @deprecated use {@link #isEnabled}
     */
    @Deprecated
    public boolean isDisabled() {
        return !executor.isEnabled(this);
    }

    /**
     * @return True if the element is enable, false otherwise.
     */
    public boolean isEnabled() {
        return executor.isEnabled(this);
    }

    /**
     * @return Whether or not the element is displayed
     */
    public boolean isDisplayed() {
        return executor.isDisplayed(this);
    }

    public WebLocator clone() throws CloneNotSupportedException {
        return (WebLocator) super.clone();
    }

    @Override
    public String toString() {
        return getPathBuilder().toString();
    }
}