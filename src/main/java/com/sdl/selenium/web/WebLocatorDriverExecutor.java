package com.sdl.selenium.web;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class WebLocatorDriverExecutor implements WebLocatorExecutor {
    private static final Logger logger = Logger.getLogger(WebLocatorDriverExecutor.class);

    private WebDriver driver;

    public WebLocatorDriverExecutor(WebDriver driver) {
        this.driver = driver;
    }

    private String currentElementPath = "";

    @Override
    public boolean doClick(WebLocator el) {
        boolean clicked = false;
//        if (highlight) {
//            doHighlight();
//        }
        if (el.currentElement != null) {
            try {
                el.currentElement.click();
                clicked = true;
            } catch (StaleElementReferenceException e) {
                logger.error("StaleElementReferenceException in doClick: " + el);
                tryAgainDoClick(el);
                clicked = true;
            } catch (InvalidElementStateException e) {
                logger.error("InvalidElementStateException in doClick: " + el);
                tryAgainDoClick(el);
                clicked = true;
            } catch (Exception e) {
                logger.error("Exception in doClick: " + el, e);
            }
        } else {
            logger.error("currentElement is null for: " + el);
        }
        return clicked;
    }

    private void tryAgainDoClick(WebLocator el) {
        el.setCurrentElementPath("");
        findElement(el);
        if (el.currentElement != null) {
            el.currentElement.click(); // not sure it will click now
        } else {
            logger.error("currentElement is null after to try currentElement: " + el);
        }
    }

    @Override
    public boolean doClickAt(WebLocator el) {
        if (highlight) {
            doHighlight(el);
        }
        focus(el);
        return doClick(el);
    }

    @Override
    public boolean isElementPresent(WebLocator el) {
        findElement(el);
        return el.currentElement != null;
    }

    @Override
    public WebElement findElement(WebLocator el) {
        final String path = el.getPath();
//        if (isSamePath(el, path)) {
//            logger.debug("currentElement already found one time: " + el);
        //return el.currentElement;
//        }
        doWaitElement(el, 0);
        el.setCurrentElementPath(path);
        return el.currentElement;
    }

    @Override
    public WebElement waitElement(final WebLocator el, final long millis) {
        doWaitElement(el, millis);
        if (el.currentElement == null) {
            logger.warn("Element not found after " + millis + " millis; " + el);
            if(WebLocatorConfig.isLogXPathEnabled()){
                logger.debug("\t" + el.getPath());
            }
        }
        return el.currentElement;
    }

    private WebElement doWaitElement(final WebLocator el, final long millis) {
        WebDriverWait wait = new WebDriverWait(driver, 0, 100);
        wait.withTimeout(millis, TimeUnit.MILLISECONDS); // hack enforce WebDriverWait to accept millis (default is seconds)
        final String xpath = el.getPath();
        try {
            el.currentElement = wait.until(new ExpectedCondition<WebElement>() {
                public WebElement apply(WebDriver driver1) {
                    return driver.findElement(By.xpath(xpath)); //TODO sa incerc sa pun id
                }
            });
        } catch (TimeoutException e) {
            el.currentElement = null;
        }
        return el.currentElement;
    }

    public boolean isSamePath(WebLocator el, String path) {
        return el.currentElement != null && (el.getCurrentElementPath().equals(path));
    }

    @Override
    public int size(WebLocator el) {
        return driver.findElements(By.xpath(el.getPath())).size();
    }

    @Override
    public void doHighlight(WebLocator el) {
        highlightElementWithDriver(el.currentElement);
    }

    @Override
    public void focus(WebLocator el) {
        doMouseOver(el);
    }

    @Override
    public String getAttribute(final WebLocator el, final String attribute) {
        String attributeValue = null;
        if (isElementPresent(el)) {
            attributeValue = getCurrentElementAttribute(el, attribute);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Element not found to getAttribute(" + attribute + "): " + el);
            }
        }
        return attributeValue;
    }

    @Override
    public String getCurrentElementAttribute(final WebLocator el, final String attribute) {
        String attributeValue = null;
        try {
            boolean exists = el.currentElement != null;
            if (exists || isElementPresent(el)) {
                if (logger.isDebugEnabled() && !exists) {
                    logger.debug("getCurrentElementAttribute: (el.currentElement was null and found after second try) " + el);
                }
                attributeValue = el.currentElement.getAttribute(attribute);
            }
        } catch (StaleElementReferenceException e) {
            logger.warn("StaleElementReferenceException in getCurrentElementAttribute(" + attribute + "): " + el);
            el.setCurrentElementPath("");
            if (isElementPresent(el)) {
                attributeValue = el.currentElement.getAttribute(attribute);
            } else {
                logger.error("StaleElementReferenceException in getCurrentElementAttribute (second try): " + attribute + ": " + el, e);
            }
        } catch (WebDriverException e) {
            logger.error("WebDriverException in getCurrentElementAttribute(" + attribute + "): " + el, e);
        }
        return attributeValue;
    }

    @Override
    public String getHtmlText(WebLocator el) {
        String text = null;
        if (isElementPresent(el)) {
            try {
                text = el.currentElement.getText();
            } catch (StaleElementReferenceException e) {
                logger.error("getHtmlText (second try): " + el.getPath(), e);
                el.setCurrentElementPath("");
                if (isElementPresent(el)) {
                    text = el.currentElement.getText();
                }
            } catch (WebDriverException e) {
                logger.error("element has vanished meanwhile: " + el.getPath(), e);
            }
        }
        return text;
    }

    @Override
    public String getHtmlSource() {
        return driver.getPageSource();
    }

    @Override
    public String getHtmlSource(WebLocator el) {
        String text = null;
        if (isElementPresent(el)) {
            text = driver.getPageSource();
        }
        return text;
    }

    @Override
    public void doSendKeys(WebLocator el, java.lang.CharSequence... charSequences) {
        try {
            el.currentElement.sendKeys(charSequences);
        } catch (ElementNotVisibleException e) {
            logger.error("ElementNotVisibleException in sendKeys: " + el, e);
            throw e;
        } catch (WebDriverException e) {
            //TODO this fix is for Chrome
            Actions builder = new Actions(driver);
            builder.click(el.currentElement);
            builder.sendKeys(charSequences);
        }
    }

    @Override
    public boolean isTextPresent(WebLocator el, String text) {
        return driver.getPageSource().contains(text);
    }

    @Override
    public boolean exists(WebLocator el) {
        return size(el) > 0;
    }

    @Override
    public boolean isSelected(WebLocator el) {
        return el.currentElement.isSelected();
    }

    @Override
    public void blur(WebLocator el) {
        fireEventWithJS(el, "blur");
    }

    @Override
    public boolean setValue(WebLocator el, String value) {
        boolean executed = false;
        if (value != null) {
            // TODO Find Solution for cases where element does not exist so we can improve cases when element is not changed
            //if (executor.isSamePath(this, this.getPath()) || ready()) {
            if (el.ready()) {
                try {
                    el.currentElement.clear();
                    el.currentElement.sendKeys(value);
                    executed = true;
                } catch (StaleElementReferenceException exception) {
                    logger.warn("StaleElementReferenceException in setValue: " + el, exception);
                    logger.warn("Set value(" + el + ") second try:  '" + value + "'");
                    if (el.ready()) {
                        el.currentElement.clear();
                        el.currentElement.sendKeys(value);
                        executed = true;
                    }
                }
                logger.info("Set value(" + el + "): '" + value + "'");
            } else {
                logger.warn("setValue : field is not ready for use: " + el);
            }
        }
        return executed;
    }

    @Override
    public String getValue(WebLocator el) {
        String value = "";
        if (el.ready()) {
            final String attributeValue = el.currentElement.getAttribute("value");
            if (attributeValue != null) {
                value = attributeValue;
            } else {
                logger.warn("getValue : value attribute is null: " + el);
            }
        } else {
            logger.warn("getValue : field is not ready for use: " + el);
        }
        return value;
    }

    @Override
    public boolean clear(WebLocator el) {
        boolean clear = false;
        if (isElementPresent(el)) {
            try {
                el.currentElement.clear();
                clear = true;
            } catch (InvalidElementStateException e) {
                clear = false;
            }
        }
        return clear;
    }

    @Override
    public boolean doubleClickAt(WebLocator el) {
        boolean clicked = false;
        try {
            Actions builder = new Actions(driver);
            builder.doubleClick(el.currentElement).perform();
            clicked = true;
        } catch (Exception e) {
            // http://code.google.com/p/selenium/issues/detail?id=244
            logger.warn("Exception in doubleClickAt", e);
            fireEventWithJS(el, "dblclick");
        }
        if(clicked){
            logger.info("doubleClickAt " + el);
        }
        return clicked;
    }

    @Override
    public void doMouseOver(WebLocator el) {
        fireEventWithJS(el, "mouseover");
    }

    public String getAttributeId(WebLocator el) {
        String pathId = getAttribute(el, "id");
        if (el.hasId()) {
            final String id = el.getId();
            if (!id.equals(pathId)) {
                logger.warn("id is not same as pathId:" + id + " - " + pathId);
            }
            return id;
        }
        return pathId;
    }

    public void fireEventWithJS(WebLocator el, String eventName) {
        String script = "if(document.createEvent){" +
                "var evObj = document.createEvent('MouseEvents');\n" +
                "evObj.initEvent('" + eventName + "', true, true);\n" +
                "fireOnThis.dispatchEvent(evObj);\n" +
                "} else if(document.createEventObject) {" +
                "fireOnThis.fireEvent('on" + eventName + "');" +
                "}";
        String id = getAttributeId(el);
        String cls;
        if (!"".equals(id)) {
            script = "var fireOnThis = document.getElementById('" + id + "');\n" + script;
        } else if (!"".equals(cls = getAttribute(el, "class"))) {
            script = "var fireOnThis = document.getElementsByClassName('" + cls + "')[0];\n" + script;
        } else {
            script = "var fireOnThis = document.evaluate(\"" + el.getPath() + "\", document, null, XPathResult.ANY_TYPE, null).iterateNext();\n" +
                    "var evObj = document.createEvent('MouseEvents');\n" +
                    "evObj.initEvent( '" + eventName + "', true, true );\n" +
                    "fireOnThis.dispatchEvent(evObj);";
        }
        Object o = executeScript(script);
        logger.debug("result executeScript: " + o);
    }

    @Override
    public Object executeScript(String script, Object... objects) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        try {
            return javascriptExecutor.executeScript(script, objects);
        } catch (WebDriverException e) {
            logger.error("WebDriverException in executeScript: " + script, e);
            return null;
        }
    }

    private void highlightElementWithDriver(WebElement el) {

        // TODO more tests for this method

//        String highlightStyle = "background: none yellow !important; color: red !important; border: 1px solid green !important;";

//        String script = "(function(element){" +
//            "var original_style = element.getAttribute('style') || '';" +
//            "element.setAttribute('style', original_style + '; " + highlightStyle + "'); " +
//            "setTimeout(function(){element.setAttribute('style', original_style);}, 200);})(arguments[0]);";

//        executeScript(script, element);

//        for (int i = 0; i < 2; i++) {
//            JavascriptExecutor js = (JavascriptExecutor) driver;
//            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, highlightStyle);
//            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
//        }
    }
}