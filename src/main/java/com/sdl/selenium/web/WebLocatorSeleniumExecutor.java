package com.sdl.selenium.web;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

public class WebLocatorSeleniumExecutor implements WebLocatorExecutor {
    private static final Logger logger = Logger.getLogger(WebLocatorSeleniumExecutor.class);

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
                logger.debug(e);
            }
        }
        return text;
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
    public boolean doMouseOver(WebLocator el) {
        selenium.mouseOver(el.getPath());
        return true;
    }

    @Override
    public Object executeScript(String script, Object... objects) {
        return selenium.getEval(script);
    }

    @Override
    public void type(WebLocator el, String text) {
        selenium.type(el.getPath(), text);
    }

    @Override
    public void typeKeys(WebLocator el, String text) {
        selenium.typeKeys(el.getPath(), text);
    }

    @Override
    public void sendKeys(WebLocator el, CharSequence... charSequences) {
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

    @Override
    public void blur(WebLocator el) {
        selenium.fireEvent(el.getPath(), "blur");
    }
}