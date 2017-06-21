package com.sdl.selenium.web;

import com.sdl.selenium.web.utils.Utils;
import com.sdl.selenium.web.utils.internationalization.InternationalizationUtils;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class WebLocator extends WebLocatorAbstractBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLocator.class);

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
     * Use xPath only
     *
     * @param attribute eg "id" or "class"
     * @return String attribute, if element not exist return null.
     */
    public String getAttribute(String attribute) {
        return executor.getAttribute(this, attribute);
    }

    protected String getCurrentElementAttribute(String attribute) {
        return executor.getCurrentElementAttribute(this, attribute);
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
        LOGGER.info("clickAt on {}", toString());
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
                LOGGER.info("clickAt on {}", toString());
            } else {
                LOGGER.info("Could not clickAt {}", toString());
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
        boolean click = waitToRender();
        assertThat("Element was not rendered " + toString(), click);
        click = executor.click(this);
        assertThat("Could not click " + toString(), click);
        LOGGER.info("click on {}", toString());
        return click;
    }

    /**
     * doClick does not make sure element is present, if you are not sure about this, please use click() instead
     *
     * @return true | false
     */
    public boolean doClick() {
        boolean doClick = waitToRender();
        if (doClick) {
            doClick = executor.click(this);
            if (doClick) {
                LOGGER.info("click on {}", toString());
            } else {
                LOGGER.info("Could not click {}", toString());
            }
        }
        return doClick;
    }

    public void highlight() {
        if (isElementPresent()) {
            doHighlight();
        }
    }

    private void doHighlight() {
        executor.highlight(this);
    }

    public WebLocator sendKeys(java.lang.CharSequence... charSequences) {
        boolean sendKeys = waitToRender();
        assertThat("Element was not rendered " + toString(), sendKeys);
        sendKeys = executor.sendKeys(this, charSequences);
        assertThat("Could not sendKeys " + toString(), sendKeys);
        LOGGER.info("sendKeys value({}): '{}'", toString(), getKeysName(charSequences));
        return this;
    }

    public WebLocator doSendKeys(java.lang.CharSequence... charSequences) {
        boolean doSendKeys = waitToRender();
        if (doSendKeys) {
            doSendKeys = executor.sendKeys(this, charSequences);
            if (doSendKeys) {
                LOGGER.info("sendKeys value({}): '{}'", toString(), getKeysName(charSequences));
            } else {
                LOGGER.info("Could not sendKeys {}", toString());
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
        LOGGER.info("clear on {}", toString());
        return clear;
    }

    public boolean doClear() {
        boolean doClear = waitToRender();
        if (doClear) {
            doClear = executor.clear(this);
            if (doClear) {
                LOGGER.info("clear on {}", toString());
            } else {
                LOGGER.info("Could not clear {}", toString());
            }
        }
        return doClear;
    }

    public boolean mouseOver() {
        boolean mouseOver = waitToRender();
        assertThat("Element was not rendered " + toString(), mouseOver);
        mouseOver = executor.mouseOver(this);
        assertThat("Could not mouse over " + toString(), mouseOver);
        LOGGER.info("mouse over on {}", toString());
        return mouseOver;
    }

    public boolean doMouseOver() {
        boolean doMouseOver = waitToRender();
        if (doMouseOver) {
            doMouseOver = executor.mouseOver(this);
            if (doMouseOver) {
                LOGGER.info("mouse over on {}", toString());
            } else {
                LOGGER.info("Could not mouse over {}", toString());
            }
        }
        return doMouseOver;
    }

    public boolean blur() {
        boolean blur = waitToRender();
        assertThat("Element was not rendered " + toString(), blur);
        blur = executor.blur(this);
        assertThat("Could not blur " + toString(), blur);
        LOGGER.info("blur on {}", toString());
        return blur;
    }

    public boolean doBlur() {
        boolean doBlur = waitToRender();
        if (doBlur) {
            doBlur = executor.blur(this);
            if (doBlur) {
                LOGGER.info("blur on {}", toString());
            } else {
                LOGGER.info("Could not blur {}", toString());
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
        LOGGER.info("focus on {}", toString());
        return this;
    }

    public WebLocator doFocus() {
        boolean doFocus = waitToRender();
        if (doFocus) {
            doFocus = executor.focus(this);
            if (doFocus) {
                LOGGER.info("focus on {}", toString());
            } else {
                LOGGER.info("Could not focus {}", toString());
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
        LOGGER.info("Double ClickAt on {}", toString());
        return doubleClickAt;
    }

    public boolean doDoubleClickAt() {
        boolean doClick = waitToRender();
        if (doClick) {
            doClick = executor.doubleClickAt(this);
            if (doClick) {
                LOGGER.info("Double click on {}", toString());
            } else {
                LOGGER.info("Could not double click {}", toString());
            }
        }
        return doClick;
    }

    /**
     * @return true | false
     */
    public boolean isElementPresent() {
        return executor.isElementPresent(this);
    }

    /**
     * driver.findElements(xpath).size()
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
    public Rectangle getRect(){
        return executor.getRect(this);
    }

    /**
     * @deprecated please use {@link #size()}
     * @return true if size is more than zero
     */
    public boolean exists() {
        return executor.exists(this);
    }

    /**
     * @deprecated please use {@link #size()}
     * @return true if size is more than zero
     */
    public boolean assertExists() {
        boolean exists = exists();
        assertThat("Element does not exists : " + this, exists);
        return exists;
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

    public boolean isVisible() {
        boolean visible = isElementPresent();
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
        return waitToRender(getPathBuilder().getRenderMillis());
    }

    public boolean waitToRender(final long millis) {
        executor.waitElement(this, millis, true);
        return currentElement != null;
    }

    public boolean waitToRender(final long millis, boolean showXPathLog) {
        executor.waitElement(this, millis, showXPathLog);
        return currentElement != null;
    }

    /**
     * @param seconds time in seconds
     * @return String
     */
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
    public String waitTextToRender(int seconds, String excludeText) {
        if (!getPathBuilder().getSearchTextType().contains(SearchType.NOT_INTERNATIONALIZED)) {
            excludeText = InternationalizationUtils.getInternationalizedText(excludeText);
        }
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
                LOGGER.debug("waitTextToRender");
            }
            Utils.sleep(200);
        }
        LOGGER.warn("No text was found for Element after " + seconds + " sec; " + this);
        return excludeText.equals(text) ? null : text;
    }

    public boolean waitToActivate() {
        return waitToActivate(getPathBuilder().getActivateSeconds());
    }

    /**
     * Wait for the element to be activated when there is deactivation mask on top of it
     *
     * @param seconds time in seconds
     * @return true | false
     */
    public boolean waitToActivate(int seconds) {
        return true;
    }

    public boolean ready() {
        return waitToRender() && waitToActivate();
    }

    public boolean assertReady() {
        boolean ready = ready();
        assertThat("Element is not ready: " + this, ready);
        return ready;
    }

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

    @Override
    public String toString() {
        return getPathBuilder().toString();
    }
}