package com.sdl.selenium.web;

import com.sdl.selenium.WebLocatorSuggestions;
import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.utils.config.WebLocatorConfig;
import com.sdl.selenium.web.utils.FileUtils;
import com.sdl.selenium.web.utils.MultiThreadClipboardUtils;
import com.sdl.selenium.web.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WebLocatorDriverExecutor implements WebLocatorExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLocatorDriverExecutor.class);

    private WebDriver driver;

    public WebLocatorDriverExecutor(WebDriver driver) {
        this.driver = driver;
    }

    private String currentElementPath = "";

    // animations or other Exception
    private static long RETRY_MS = 500;

    @Override
    public boolean click(WebLocator el) {
        boolean clicked = false;
//        if (highlight) {
//            doHighlight();
//        }
        if (ensureExists(el)) {
            try {
                el.currentElement.click();
                clicked = true;
            } catch (StaleElementReferenceException e) {
                clicked = tryAgainDoClick(el);
            } catch (InvalidElementStateException e) {
                LOGGER.error("InvalidElementStateException in doClick: {}", el);
                clicked = tryAgainDoClick(el);
            } catch (MoveTargetOutOfBoundsException e) {
                LOGGER.error("MoveTargetOutOfBoundsException in doClick: {}", el);
                clicked = tryAgainDoClick(el);
            } catch (WebDriverException e) {
                LOGGER.error("WebDriverException in doClick: {}", el);
                clicked = tryAgainDoClick(el);
            } catch (Exception e) {
                LOGGER.error("Exception in doClick: {} - {}", el, e);
            }
        }
        return clicked;
    }

    private boolean tryAgainDoClick(WebLocator el) {
        boolean doClick;
        if (findAgain(el)) {
            el.currentElement.click(); // not sure it will click now
            doClick = true;
        } else {
            LOGGER.error("currentElement is null after to try currentElement: {}", el);
            doClick = false;
        }
        return doClick;
    }

    @Override
    public boolean clickAt(WebLocator el) {
//        if (highlight) {
//            doHighlight(el);
//        }
        focus(el);
        return click(el);
    }

    @Override
    public boolean doubleClickAt(WebLocator el) {
        boolean clicked = false;
        if (ensureExists(el)) {
            try {
                Actions builder = new Actions(driver);
                builder.doubleClick(el.currentElement).perform();
                clicked = true;
            } catch (Exception e) {
                // http://code.google.com/p/selenium/issues/detail?id=244
                LOGGER.info("Exception in doubleClickAt {}", e);
                clicked = fireEventWithJS(el, "dblclick") != null;
            }
        }
        return clicked;
    }

    public boolean submit(WebLocator el) {
        boolean submit = false;
        if (ensureExists(el)) {
            try {
                el.currentElement.submit();
                submit = true;
            } catch (StaleElementReferenceException e) {
                submit = tryAgainDoSubmit(el);
            } catch (InvalidElementStateException e) {
                LOGGER.error("InvalidElementStateException in doClick: {}", el);
                submit = tryAgainDoSubmit(el);
            } catch (MoveTargetOutOfBoundsException e) {
                LOGGER.error("MoveTargetOutOfBoundsException in doClick: {}", el);
                submit = tryAgainDoSubmit(el);
            } catch (WebDriverException e) {
                LOGGER.error("WebDriverException in doClick: {}", el);
                submit = tryAgainDoSubmit(el);
            } catch (Exception e) {
                LOGGER.error("Exception in doClick: {}", el, e);
            }
        }
        return submit;
    }

    private boolean tryAgainDoSubmit(WebLocator el) {
        boolean submit;
        if (findAgain(el)) {
            el.currentElement.submit(); // not sure it will click now
            submit = true;
        } else {
            LOGGER.error("currentElement is null after to try currentElement: {}", el);
            submit = false;
        }
        return submit;
    }

    @Override
    public boolean clear(WebLocator el) {
        boolean clear = false;
        if (ensureExists(el)) {
            try {
                el.currentElement.clear();
                clear = true;
            } catch (InvalidElementStateException e) {
                LOGGER.warn("InvalidElementStateException clear: {}", el);
                clear = false;
            }
        }
        return clear;
    }

    @Override
    public boolean sendKeys(WebLocator el, java.lang.CharSequence... charSequences) {
        boolean sendKeys = false;
        if (ensureExists(el)) {
            try {
                el.currentElement.sendKeys(charSequences);
                sendKeys = true;
            } catch (ElementNotVisibleException e) {
                try {
                    sendKeys = tryAgainDoSendKeys(el, charSequences);
                } catch (ElementNotVisibleException ex) {
                    try {
                        mouseOver(el);
                        sendKeys = tryAgainDoSendKeys(el, charSequences);
                    } catch (ElementNotVisibleException exc) {
                        LOGGER.error("final ElementNotVisibleException in sendKeys: {}", el, exc);
                        throw exc;
                    }
                }
            } catch (WebDriverException e) {
                //TODO this fix is for Chrome
                Actions builder = new Actions(driver);
                builder.click(el.currentElement);
                builder.sendKeys(charSequences);
                sendKeys = true;
            }
        }
        return sendKeys;
    }

    private boolean tryAgainDoSendKeys(WebLocator el, java.lang.CharSequence... charSequences) {
        if (findAgain(el)) {
            el.currentElement.sendKeys(charSequences); // not sure it will click now
            return true;
        } else {
            LOGGER.error("currentElement is null after to try currentElement: {}", el);
            return false;
        }
    }

    private boolean setValue(WebLocator el, String value, int retries) {
        boolean executed = false;
        if (retries >= 0 && value != null) {
            retries--;
            // TODO Find Solution for cases where element does not exist so we can improve cases when element is not changed
            //if (executor.isSamePath(this, this.getXPath()) || ready()) {
            if (ensureExists(el)) {
                try {
                    executed = doSetValue(el, value);
                } catch (ElementNotVisibleException exception) {
                    LOGGER.error("ElementNotVisibleException in setValue: {}", el, exception);
                    throw exception;
                } catch (InvalidElementStateException ex) {
                    invalidateCache(el);
                    if (WebLocatorConfig.isLogRetryException()) {
                        LOGGER.debug("Exception in setValue: {}. {}", el, ex);
                    }
                    if (retries >= 0) {
                        LOGGER.debug("Exception in setValue: {}. Wait {} ms before retry", el, RETRY_MS);
                        Utils.sleep(RETRY_MS);
                    }
                    executed = setValue(el, value, retries);
                } catch (StaleElementReferenceException ex) {
                    invalidateCache(el);
                    if (retries >= 0) {
                        Utils.sleep(RETRY_MS);
                    }
                    executed = setValue(el, value, retries);
                }
            }
        }
        return executed;
    }

    @Override
    public boolean setValue(WebLocator el, String value) {
        return setValue(el, value, 1);
    }

    private boolean doSetValue(WebLocator el, String value) {
        int lengthVal = WebLocatorConfig.getMinCharsToType();
        int length = value.length();
        el.currentElement.clear();
        if (lengthVal == -1 || length <= lengthVal) {
            el.currentElement.sendKeys(value);
            LOGGER.info("Set value({}): '{}'", el, getLogValue(el, value));
        } else {
            try {
                MultiThreadClipboardUtils.copyString(StringUtils.chop(value));
            } catch (IllegalStateException e) {
                LOGGER.debug("IllegalStateException: cannot open system clipboard - try again.");
                MultiThreadClipboardUtils.copyString(StringUtils.chop(value));
            }
            MultiThreadClipboardUtils.pasteString(el);
            el.currentElement.sendKeys(value.substring(length - 1));
            LOGGER.info("Paste value({}): string with size: '{}'", el, length);
        }
        return true;
    }

    private String getLogValue(WebLocator el, String value) {
        String info = el.getPathBuilder().getInfoMessage();
        if (info == null || "".equals(info)) {
            info = el.getPathBuilder().itemToString();
        }
        info = info.toLowerCase();

        return WebLocatorConfig.getLogParamsExclude().contains(info) ? "*****" : value;
    }

    @Override
    public String getCssValue(final WebLocator el, final String propertyName) {
        return ensureExists(el) ? el.currentElement.getCssValue(propertyName) : null;
    }

    @Override
    public String getTagName(final WebLocator el) {
        return ensureExists(el) ? el.currentElement.getTagName() : null;
    }

    @Override
    public String getAttribute(final WebLocator el, final String attribute) {
        return ensureExists(el) ? getCurrentElementAttribute(el, attribute) : null;
    }

    public String getAttributeId(WebLocator el) {
        String pathId = getAttribute(el, "id");
        if (el.hasId()) {
            final String id = el.getPathBuilder().getId();
            if (!id.equals(pathId)) {
                LOGGER.warn("id is not same as pathId:{} - {}", id, pathId);
            }
            return id;
        }
        return pathId;
    }

    private boolean ensureExists(final WebLocator el) {
        boolean present = el.currentElement != null || isElementPresent(el);
        if (!present) {
            LOGGER.debug("Element not found: {}", el);
        }
        return present;
    }

    @Override
    public String getCurrentElementAttribute(final WebLocator el, final String attribute) {
        String attributeValue = null;
        try {
            if (ensureExists(el)) {
                attributeValue = el.currentElement.getAttribute(attribute);
            }
        } catch (StaleElementReferenceException e) {
            if (findAgain(el)) {
                attributeValue = el.currentElement.getAttribute(attribute);
            } else {
                LOGGER.error("StaleElementReferenceException in getCurrentElementAttribute (second try): {}: {} - {}", attribute, el, e);
            }
        } catch (WebDriverException e) {
            LOGGER.error("WebDriverException in getCurrentElementAttribute({}): {} - {}", attribute, el, e);
        }
        return attributeValue;
    }

    @Override
    public String getText(WebLocator el) {
        String text = null;
        if (ensureExists(el)) {
            try {
                text = el.currentElement.getText();
            } catch (StaleElementReferenceException e) {
                if (findAgain(el)) {
                    text = el.currentElement.getText();
                }
            } catch (WebDriverException e) {
                LOGGER.error("element has vanished meanwhile: " + getSelector(el), e);
            }
        }
        return text;
    }

    private String getSelector(WebLocator el) {
        String css = el.getCssSelector();
        return StringUtils.isEmpty(css) ? el.getXPath() : css;
    }

    private boolean findAgain(WebLocator el) {
        invalidateCache(el);
        return isElementPresent(el);
    }

    private void invalidateCache(WebLocator el) {
        el.currentElement = null;
        el.setCurrentElementPath("");
    }

    @Override
    public String getValue(WebLocator el) {
        return getAttribute(el, "value");
    }

    @Override
    public boolean isElementPresent(WebLocator el) {
        findElement(el);
        return el.currentElement != null;
    }

    @Override
    public WebElement findElement(WebLocator el) {
        final String path = getSelector(el);
//        if (isSamePath(el, path)) {
//            LOGGER.debug("currentElement already found one time: " + el);
        //return el.currentElement;
//        }
        doWaitElement(el, 0);
        el.setCurrentElementPath(path);
        return el.currentElement;
    }

    public List<WebElement> findElements(WebLocator el) {
        return driver.findElements(el.getSelector());
    }

    @Override
    public WebElement waitElement(final WebLocator el, final long millis) {
        doWaitElement(el, millis);
        if (el.currentElement == null) {
            LOGGER.warn("Element not found after " + millis + " millis; {}", el);
            logDetails(el);
        }
        return el.currentElement;
    }

    private void logDetails(WebLocator el) {
        if (WebLocatorConfig.isLogXPath()) {
            LOGGER.debug("\t" + WebLocatorUtils.getFirebugXPath(el));
        }
        if (WebLocatorConfig.isLogSuggestions()) {
            WebLocatorSuggestions.getSuggestion(el);
        }
    }

    private WebElement doWaitElement(final WebLocator el, final long millis) {
        WebDriverWait wait = new WebDriverWait(driver, 0, 100);
        wait.withTimeout(millis, TimeUnit.MILLISECONDS); // hack enforce WebDriverWait to accept millis (default is seconds)
        try {
            if (el.getPathBuilder().isVisibility()) {
                el.currentElement = wait.until(ExpectedConditions.visibilityOfElementLocated(el.getSelector()));
            } else {
                el.currentElement = wait.until(new ExpectedCondition<WebElement>() {
                    public WebElement apply(WebDriver driver1) {
                        return driver.findElement(el.getSelector());
                    }
                });
            }
        } catch (TimeoutException e) {
            el.currentElement = null;
        }
        return el.currentElement;
    }

    @Override
    public int size(WebLocator el) {
        return findElements(el).size();
    }

    @Override
    public Point getLocation(WebLocator el) {
        return ensureExists(el) ? el.currentElement.getLocation() : null;
    }

    @Override
    public Dimension getSize(WebLocator el) {
        return ensureExists(el) ? el.currentElement.getSize() : null;
    }

    @Override
    public Rectangle getRect(WebLocator el) {
        return ensureExists(el) ? el.currentElement.getRect() : null;
    }

    @Override
    public boolean focus(WebLocator el) {
        return fireEventWithJS(el, "mouseover") != null;
    }

    @Override
    public boolean mouseOver(WebLocator el) {
        boolean mouseOver;
        try {
            if (ensureExists(el)) {
                Actions builder = new Actions(driver);
                builder.moveToElement(el.currentElement).perform();
                mouseOver = true;
            } else {
                mouseOver = false;
            }
        } catch (WebDriverException e) {
            LOGGER.error("Could not mouse over {}, {}", el, e);
            mouseOver = false;
        }
        return mouseOver;
    }

    @Override
    public boolean blur(WebLocator el) {
        return fireEventWithJS(el, "blur") != null;
    }


    @Override
    public boolean exists(WebLocator el) {
        return ensureExists(el) && size(el) > 0;
    }

    @Override
    public boolean isSelected(WebLocator el) {
        return ensureExists(el) && el.currentElement.isSelected();
    }

    public boolean isDisplayed(WebLocator el) {
        return ensureExists(el) && el.currentElement.isDisplayed();
    }

    public boolean isEnabled(WebLocator el) {
        return ensureExists(el) && el.currentElement.isEnabled();
    }

    public boolean isSamePath(WebLocator el, String path) {
        return el.currentElement != null && (el.getCurrentElementPath().equals(path));
    }

    private boolean isCached(WebLocator el) {
        boolean cached = false; // TODO config
        return cached;
    }

    @Override
    public Object executeScript(String script, Object... objects) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        try {
            return javascriptExecutor.executeScript(script, objects);
        } catch (WebDriverException e) {
            LOGGER.error("WebDriverException in executeScript: " + script, e);
            return null;
        }
    }

    public Object fireEventWithJS(WebLocator el, String eventName) {
        String script = "if(document.createEvent){" +
                "var evObj = document.createEvent('MouseEvents');\n" +
                "evObj.initEvent('" + eventName + "', true, true);\n" +
                "return fireOnThis.dispatchEvent(evObj);\n" +
                "} else if(document.createEventObject) {" +
                "return fireOnThis.fireEvent('on" + eventName + "');" +
                "}";
        String id = getAttributeId(el);
        String cls;
        if (!"".equals(id)) {
            script = "var fireOnThis = document.getElementById('" + id + "');\n" + script;
        } else if (!"".equals(cls = getAttribute(el, "class"))) {
            script = "var fireOnThis = document.getElementsByClassName('" + cls + "')[0];\n" + script;
        } else {
            script = "var fireOnThis = document.evaluate(\"" + el.getXPath() + "\", document, null, XPathResult.ANY_TYPE, null).iterateNext();\n" +
                    "var evObj = document.createEvent('MouseEvents');\n" +
                    "evObj.initEvent( '" + eventName + "', true, true );\n" +
                    "return fireOnThis.dispatchEvent(evObj);";
        }
        return executeScript(script);
    }

    @Override
    public void highlight(WebLocator el) {
        highlightElementWithDriver(el.currentElement);
    }

    public boolean download(String fileName, long timeoutMillis) {
        if (WebDriverConfig.isSilentDownload()) {
            fileName = WebDriverConfig.getDownloadPath() + File.separator + fileName;
            File file = new File(fileName);
            return FileUtils.waitFileIfIsEmpty(file, timeoutMillis) && fileName.equals(file.getAbsolutePath());
        } else {
            return RunExe.getInstance().download(fileName);
        }
    }

    public boolean browse(WebLocator el) {
        try {
            el.focus();
            Actions builder = new Actions(driver);
            builder.moveToElement(el.currentElement).perform();
            builder.click().perform();
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * @param filePath e.g. "C:\\text.txt"
     * @return true of false
     */
    public boolean upload(String filePath) {
        return RunExe.getInstance().upload(filePath);
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