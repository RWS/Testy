package com.sdl.selenium.web;

import com.sdl.selenium.web.utils.Utils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
     * @return Element value of css property
     */
    public String getCssValue(String propertyName) {
        return executor.getCssValue(this, propertyName);
    }

    /**
     * @return tag name
     */
    public String getTagName() {
        return executor.getTagName(this);
    }

    /**
     * @return id
     */
    public String getAttributeId() {
        return getAttribute("id");
    }

    /**
     * @return class
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

    public String getHtmlText() {
        return getHtmlText(false);
    }

    public String getHtmlText(boolean instant) {
        if (instant || ready()) {
            return executor.getHtmlText(this);
        }
        return null;
    }

    public boolean clickAt() {
        return doClickAt();
    }

    /**
     * doClickAt does not make sure element is present, if you are not sure about this, please use click() instead
     *
     * @return true | false
     */
    public boolean doClickAt() {
        boolean doClick = waitToRender();
        if (doClick) {
            doClick = executor.doClickAt(this);
            if (doClick) {
                LOGGER.info("clickAt on {}", toString());
            } else {
                LOGGER.info("Could not clickAt {}", toString());
            }
        }
        return doClick;
    }

    public boolean assertClickAt() {
        boolean clickAt = waitToRender();
        assertThat("Element was not rendered " + toString(), clickAt);
        clickAt = executor.doClickAt(this);
        assertThat("Could not clickAt " + toString(), clickAt);
        LOGGER.info("clickAt on {}", toString());
        return clickAt;
    }

    public boolean assertExists() {
        boolean exists = exists();
        assertThat("Element does not exists : " + this, exists);
        return exists;
    }

    /**
     * Click once do you catch exceptions StaleElementReferenceException.
     *
     * @return true | false
     */
    public boolean click() {
        return doClick();
    }

    /**
     * doClick does not make sure element is present, if you are not sure about this, please use click() instead
     *
     * @return true | false
     */
    public boolean doClick() {
        boolean doClick = waitToRender();
        if (doClick) {
            doClick = executor.doClick(this);
            if (doClick) {
                LOGGER.info("click on {}", toString());
            } else {
                LOGGER.info("Could not click {}", toString());
            }
        }
        return doClick;
    }

    /**
     * //TODO Daca nu a facut click pe element sa se intrerupa testul de facut si pentru clickAt() si sa fie setabil
     *
     * @return true
     */

    public boolean assertClick() {
        boolean click = waitToRender();
        assertThat("Element was not rendered " + toString(), click);
        click = executor.doClick(this);
        assertThat("Could not click " + toString(), click);
        LOGGER.info("click on {}", toString());
        return click;
    }

    public void highlight() {
        if (isElementPresent()) {
            doHighlight();
        }
    }

    private void doHighlight() {
        executor.doHighlight(this);
    }

    public WebLocator sendKeys(java.lang.CharSequence... charSequences) {
        if (ready()) {
            executor.doSendKeys(this, charSequences);
        } else {
            LOGGER.debug("Element is not ready " + toString());
            return null;
        }
        return this;
    }

    public boolean clear() {
        return doClear();
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
        try {
            executor.doMouseOver(this);
            mouseOver = true;
        } catch (Exception e) {
            mouseOver = false;
        }
        assertThat("Could not mouse over " + toString(), mouseOver);
        LOGGER.info("Mouse over on {}", toString());
        return mouseOver;
    }

    public boolean doMouseOver() {
        boolean doMouseOver = false;
        if (waitToRender()) {
            try {
                executor.doMouseOver(this);
                doMouseOver = true;
                LOGGER.info("Mouse over on {}", toString());
            } catch (Exception e) {
                LOGGER.error("Could not mouse over {}, {}", toString(), e);
            }
        }
        return doMouseOver;
    }

    public boolean blur() {
        return doBlur();
    }

    public boolean doBlur() {
        if (ready()) {
            LOGGER.info("blur on {}", this);
            executor.blur(this);
            return true;
        } else {
            LOGGER.warn("blur on {} failed", this);
            return false;
        }
    }

    /**
     * Using XPath only
     *
     * @return true | false
     */
    public WebLocator focus() {
        return doFocus();
    }

    public WebLocator doFocus() {
        if (ready()) {
            executor.focus(this);
            LOGGER.info("focus on {}", this);
        }
        return this;
    }

    /**
     * @return true | false
     */
    public boolean doubleClickAt() {
        return doDoubleClickAt();
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

    public Point getLocation() {
        return executor.getLocation(this);
    }

    public Dimension getSize() {
        return executor.getSize(this);
    }

    public boolean exists() {
        return executor.exists(this);
    }

    // TODO see where is used and if is necessary to be public
    public WebElement findElement() {
        return executor.findElement(this);
    }

    public List<WebElement> findElements() {
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

    // TODO remove from this class, it does not belong to this element

    /**
     * @deprecated please use driver.getPageSource().contains(text);
     */
    public boolean isTextPresent(String text) {
        return executor.isTextPresent(this, text);
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
        executor.waitElement(this, millis);
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
        String text = null;
        if (seconds == 0 && ((text = getHtmlText(true)) != null && text.length() > 0 && !text.equals(excludeText))) {
            return text;
        }
        for (int i = 0, count = 5 * seconds; i < count; i++) {
            text = getHtmlText(true);
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
        assertThat("Element is not ready : " + this, ready);
        return ready;
    }

    public boolean ready(int seconds) {
        return waitToRender(seconds * 1000) && waitToActivate(seconds);
    }

    public boolean isDisabled() {
        return ready() && !currentElement.isEnabled();
    }

    public boolean isDisplayed() {
        return executor.isDisplayed(this);
    }

    @Override
    public String toString() {
        return getPathBuilder().toString();
    }
}