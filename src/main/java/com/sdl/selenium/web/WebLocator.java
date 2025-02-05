package com.sdl.selenium.web;

import com.sdl.selenium.WebLocatorSuggestions;
import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.utils.Utils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.locators.RelativeLocator;
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

    public WebLocator(WebElement webElement) {
        setWebElement(webElement);
    }

    public WebLocator(SearchContext shadowRoot) {
        setShadowRoot(shadowRoot);
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

    public WebLocator above(WebLocator locator) {
        WebElement el = WebDriverConfig.getDriver().findElement(RelativeLocator.with(By.xpath(getXPath())).above(locator.getWebElement()));
        return new WebLocator(el);
    }

    public WebLocator below(WebLocator locator) {
        WebElement el = WebDriverConfig.getDriver().findElement(RelativeLocator.with(By.xpath(getXPath())).below(locator.getWebElement()));
        return new WebLocator(el);
    }

    public WebLocator near(WebLocator locator) {
        WebElement el = WebDriverConfig.getDriver().findElement(RelativeLocator.with(By.xpath(getXPath())).near(locator.getWebElement()));
        return new WebLocator(el);
    }

    public WebLocator toLeftOf(WebLocator locator) {
        WebElement el = WebDriverConfig.getDriver().findElement(RelativeLocator.with(By.xpath(getXPath())).toLeftOf(locator.getWebElement()));
        return new WebLocator(el);
    }

    public WebLocator toRightOf(WebLocator locator) {
        WebElement el = WebDriverConfig.getDriver().findElement(RelativeLocator.with(By.xpath(getXPath())).toRightOf(locator.getWebElement()));
        return new WebLocator(el);
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
     * @param attribute eg "id" or "class"
     * @return String attribute, if element not exist return null.
     */
    public String getAttribute(String attribute) {
        return executor.getAttribute(this, attribute);
    }

    /**
     * @param attribute eg "id" or "class"
     * @param instant   true or false
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
            return executor.getText(this, instant);
        }
        return null;
    }

    public boolean clickAt() {
        boolean clickAt = waitToRender();
        assertThat("Element was not rendered " + this, clickAt);
        clickAt = executor.clickAt(this);
        assertThat("Could not clickAt " + this, clickAt);
        log.info("clickAt on {}", this);
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
                log.info("clickAt on {}", this);
            } else {
                log.info("Could not clickAt {}", this);
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
        assertThat("Element was not rendered " + this, click);
        click = executor.click(this);
        assertThat("Could not click " + this, click);
        if (showLog) {
            log.info("click on {}", this);
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
                    log.info("click on {}", this);
                } else {
                    log.info("Could not click {}", this);
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
        assertThat("Element was not rendered " + this, sendKeys);
        sendKeys = executor.sendKeys(showLog, this, charSequences);
        assertThat("Could not sendKeys " + this, sendKeys);
        return this;
    }

    public WebLocator doSendKeys(java.lang.CharSequence... charSequences) {
        return doSendKeys(true, charSequences);
    }

    public WebLocator doSendKeys(boolean showLog, java.lang.CharSequence... charSequences) {
        boolean doSendKeys = waitToRender();
        if (doSendKeys) {
            doSendKeys = executor.sendKeys(showLog, this, charSequences);
        }
        return doSendKeys ? this : null;
    }

    public boolean clear() {
        boolean clear = waitToRender();
        assertThat("Element was not rendered " + this, clear);
        clear = executor.clear(this);
        assertThat("Could not clear " + this, clear);
        log.info("clear on {}", this);
        return clear;
    }

    public boolean doClear() {
        boolean doClear = waitToRender();
        if (doClear) {
            doClear = executor.clear(this);
            if (doClear) {
                log.info("clear on {}", this);
            } else {
                log.info("Could not clear {}", this);
            }
        }
        return doClear;
    }

    public boolean mouseOver() {
        boolean mouseOver = waitToRender();
        assertThat("Element was not rendered " + this, mouseOver);
        mouseOver = executor.mouseOver(this);
        assertThat("Could not mouse over " + this, mouseOver);
        log.info("mouse over on {}", this);
        return mouseOver;
    }

    public boolean doMouseOver() {
        boolean doMouseOver = waitToRender();
        if (doMouseOver) {
            doMouseOver = executor.mouseOver(this);
            if (doMouseOver) {
                log.info("mouse over on {}", this);
            } else {
                log.info("Could not mouse over {}", this);
            }
        }
        return doMouseOver;
    }

    public boolean blur() {
        boolean blur = waitToRender();
        assertThat("Element was not rendered " + this, blur);
        blur = executor.blur(this);
        assertThat("Could not blur " + this, blur);
        log.info("blur on {}", this);
        return blur;
    }

    public boolean doBlur() {
        boolean doBlur = waitToRender();
        if (doBlur) {
            doBlur = executor.blur(this);
            if (doBlur) {
                log.info("blur on {}", this);
            } else {
                log.info("Could not blur {}", this);
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
        assertThat("Element was not rendered " + this, focus);
        focus = executor.focus(this);
        assertThat("Could not focus " + this, focus);
        log.info("focus on {}", this);
        return this;
    }

    public WebLocator doFocus() {
        boolean doFocus = waitToRender();
        if (doFocus) {
            doFocus = executor.focus(this);
            if (doFocus) {
                log.info("focus on {}", this);
            } else {
                log.info("Could not focus {}", this);
            }
        }
        return doFocus ? this : null;
    }

    /**
     * @return true | false
     */
    public boolean doubleClickAt() {
        boolean doubleClickAt = waitToRender();
        assertThat("Element was not rendered " + this, doubleClickAt);
        doubleClickAt = executor.doubleClickAt(this);
        assertThat("Could not Double ClickAt " + this, doubleClickAt);
        log.info("Double ClickAt on {}", this);
        return doubleClickAt;
    }

    public boolean doDoubleClickAt() {
        boolean doClick = waitToRender();
        if (doClick) {
            doClick = executor.doubleClickAt(this);
            if (doClick) {
                log.info("Double click on {}", this);
            } else {
                log.info("Could not double click {}", this);
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
        assertThat("Elements were not rendered " + this, findElements);
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
        return waitToRender(Duration.ofSeconds(seconds)) && waitToActivate(seconds);
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

    public String getAccessibleName() {
        return executor.getAccessibleName(this);
    }

    public String getAriaRole() {
        return executor.getAriaRole(this);
    }

    public String getDomAttribute(String name) {
        return executor.getDomAttribute(this, name);
    }

    public String getDomProperty(String name) {
        return executor.getDomProperty(this, name);
    }

    public SearchContext getShadowRoot() {
        return executor.getShadowRoot(this);
    }

    public WebLocator clone() {
        return WebLocatorSuggestions.getClone(this);
    }

    @Override
    public String toString() {
        return getPathBuilder().toString();
    }
}