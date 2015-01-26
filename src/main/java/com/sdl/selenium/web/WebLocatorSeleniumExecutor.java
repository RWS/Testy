package com.sdl.selenium.web;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebElement;

/**
 * @deprecated The RC interface will be removed in Selenium 3.0. Please migrate to using WebDriver.
 */
public class WebLocatorSeleniumExecutor implements WebLocatorExecutor {
    private static final Logger logger = LoggerFactory.getLogger(WebLocatorSeleniumExecutor.class);

    public Selenium selenium;

    public WebLocatorSeleniumExecutor(Selenium selenium) {
        this.selenium = selenium;
    }

    @Override
    public boolean doClick(WebLocator el) {
        if (highlight) {
            doHighlight(el);
        }
        selenium.click(el.getPath());
        return true;
    }

    @Override
    public boolean doClickAt(WebLocator el) {
        if (highlight) {
            doHighlight(el);
        }
        selenium.clickAt(el.getPath(), "");
        return true;
    }

    @Override
    public boolean isElementPresent(WebLocator el) {
        return selenium.isElementPresent(el.getPath());
    }

    @Override
    public WebElement findElement(WebLocator el) {
        return null;
    }

    public WebElement waitElement(final WebLocator el, final long millis) {
        return null;
    }

    @Override
    public int size(WebLocator el) {
        return selenium.getXpathCount(el.getPath()).intValue();
    }

    @Override
    public void doHighlight(WebLocator el) {
        selenium.highlight(el.getPath());
    }

    @Override
    public void focus(WebLocator el) {
        selenium.focus(el.getPath());
    }

    @Override
    public String getAttribute(final WebLocator el, final String attribute) {
        String attributeValue = null;
        if (isElementPresent(el)) {
            attributeValue = getCurrentElementAttribute(el, attribute);
        }
        return attributeValue;
    }

    @Override
    public String getCurrentElementAttribute(final WebLocator el, final String attribute) {
        String attributeValue = null;
        try {
            attributeValue = selenium.getAttribute(el.getPath() + "@" + attribute); //TODO
        } catch (SeleniumException e) {
            logger.debug("getAttribute '" + attribute + "' SeleniumException: " + e);
        }
        return attributeValue;
    }

    @Override
    public String getHtmlText(WebLocator el) {
        String text = null;
        if (isElementPresent(el)) {
            try {
                text = selenium.getText(el.getPath());
            } catch (SeleniumException e) {
                logger.debug("element has vanished meanwhile: " + el.getPath());
                logger.debug("SeleniumException: {}", e);
            }
        }
        return text;
    }

    @Override
    public String getHtmlSource() {
        return selenium.getBodyText();
    }

    @Override
    public String getHtmlSource(WebLocator el) {
        String text = null;
        if (isElementPresent(el)) {
            text = selenium.getBodyText();
        }
        return text;
    }

    @Override
    public boolean clear(WebLocator el) {
        boolean clear = false;
        if (isElementPresent(el)) {
            String path = el.getPath();
            selenium.focus(path); // to scroll to this element (if element is not visible)
            selenium.type(path, "");
//                selenium.keyUp(path, "");  //TODO
            selenium.fireEvent(path, "blur");
        }
        return clear;
    }

    @Override
    public boolean doubleClickAt(WebLocator el) {
        boolean clicked;
        selenium.doubleClickAt(el.getPath(), "");
        clicked = true;
        logger.info("doubleClickAt " + toString());
        return clicked;
    }

    @Override
    public void doMouseOver(WebLocator el) {
        selenium.mouseOver(el.getPath());
    }

    @Override
    public Object executeScript(String script, Object... objects) {
        return selenium.getEval(script);
    }

    @Override
    public void doSendKeys(WebLocator el, CharSequence... charSequences) {
        logger.warn("TODO not implemented yet for selenium");
    }

    @Override
    public boolean isTextPresent(WebLocator el, String text) {
        return selenium.isTextPresent(text);
    }

    @Override
    public boolean exists(WebLocator el) {
        return isElementPresent(el);
    }

    @Override
    public boolean isSelected(WebLocator el) {
        return selenium.isChecked(el.getPath());
    }

    public boolean isSamePath(WebLocator el, String path) {
        return false;
    }

    @Override
    public void blur(WebLocator el) {
        selenium.fireEvent(el.getPath(), "blur");
    }

    @Override
    public boolean setValue(WebLocator el, String value) {
        if (value != null) {
            if (el.ready()) {
                String path = el.getPath();
                selenium.focus(path); // to scroll to this element (if element is not visible)
                selenium.type(path, value);
                selenium.keyUp(path, value.substring(value.length() - 1));
                selenium.fireEvent(path, "blur");
                return true;
            } else {
                logger.warn("getValue : field is not ready for use: " + this);
            }
        }
        return false;
    }

    @Override
    public String getValue(WebLocator el) {
        if (el.ready()) {
            return selenium.getValue(el.getPath());
        } else {
            logger.warn("getValue : field is not ready for use: " + this);
            return "";
        }
    }
}