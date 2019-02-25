package com.sdl.selenium.web;

import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public abstract class Locator implements ILocator, IActions {

    /**
     * @return The tag name of this element.
     */
    public String getTagName() {
        return executor().getTagName(this);
    }

    /**
     * @param propertyName property name
     * @return Element value of css property
     */
    public String getCssValue(String propertyName) {
        return executor().getCssValue(this, propertyName);
    }

    /**
     * @return The id of this element.
     */
    public String getAttributeId() {
        return executor().getAttributeId(this);
    }

    /**
     * @return The classes of this element.
     */
    public String getAttributeClass() {
        return executor().getAttribute(this, "class");
    }

    /**
     * @return This method return the WebElement
     */
    public WebElement getWebElement() {
        return executor().findElement(this);
    }

    /**
     * @param attribute e.g. class, id
     * @return String attribute, if element not exist return null.
     */
    public String getAttribute(String attribute) {
        return executor().getAttribute(this, attribute);
    }

    public boolean isElementPresent() {
        return executor().isElementPresent(this);
    }

    /**
     * driver.findElements(xpath).size()
     *
     * @return the number of elements in this list
     */
    public int size() {
        return executor().size(this);
    }

    public WebElement findElement() {
        return executor().findElement(this);
    }

    public List<WebElement> findElements() {
        boolean findElements = waitToRender();
        assertThat("Elements were not rendered " + toString(), findElements);
        return executor().findElements(this);
    }

    /**
     * @return True if the element is enable, false otherwise.
     */
    public boolean isEnabled() {
        return executor().isEnabled(this);
    }

    /**
     * wait 5 seconds (or specified value for renderSeconds)
     *
     * @return true | false
     */
    public boolean waitToRender() {
        return waitToRender(getXPathBuilder().getRenderMillis());
    }

    public boolean waitToRender(final long millis) {
        return waitToRender(millis, true);
    }

    public boolean waitToRender(final long millis, boolean showXPathLog) {
        executor().waitElement(this, Duration.ofMillis(millis), showXPathLog);
        return getWebElement() != null;
    }

    public boolean ready() {
        return ready(getXPathBuilder().getActivateSeconds());
    }

    public boolean ready(int seconds) {
        return waitToRender(seconds * 1000);
    }
}