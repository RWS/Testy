package com.sdl.selenium.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.utils.config.WebLocatorConfig;
import com.sdl.selenium.web.utils.FileUtils;
import com.sdl.selenium.web.utils.MultiThreadClipboardUtils;
import com.sdl.selenium.web.utils.RetryUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public boolean click(WebLocatorOld el) {
//        if (highlight) {
//            doHighlight();
//        }
        Boolean click = RetryUtils.retrySafe(6, () -> {
            findAgain(el);
            el.getWebElement().click();
            return el.currentElement != null;
        });
        return click == null ? false : click;
    }

    @Override
    public boolean clickAt(WebLocatorOld el) {
        focus(el);
        return click(el);
    }

    @Override
    public boolean doubleClickAt(WebLocatorOld el) {
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

    public boolean submit(WebLocatorOld el) {
        Boolean submit = RetryUtils.retry(6, () -> {
            findAgain(el);
            el.getWebElement().submit();
            return el.currentElement != null;
        });
        return submit == null ? false : submit;
    }

    @Override
    public boolean clear(WebLocatorOld el) {
        Boolean clear = RetryUtils.retry(6, () -> {
            findAgain(el);
            el.getWebElement().clear();
            return el.currentElement != null;
        });
        return clear == null ? false : clear;
    }

    @Override
    public boolean sendKeys(WebLocatorOld el, java.lang.CharSequence... charSequences) {
        boolean sendKeys = false;
        if (ensureExists(el)) {
            try {
                el.getWebElement().sendKeys(charSequences);
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

    private boolean tryAgainDoSendKeys(WebLocatorOld el, java.lang.CharSequence... charSequences) {
        if (findAgain(el)) {
            el.getWebElement().sendKeys(charSequences); // not sure it will click now
            return true;
        } else {
            LOGGER.error("currentElement is null after to try currentElement: {}", el);
            return false;
        }
    }

    @Override
    public boolean setValue(WebLocatorOld el, String value) {
        Boolean retry = RetryUtils.retry(6, () -> doSetValue(el, value));
        return retry == null ? false : retry;
    }

    private boolean doSetValue(WebLocatorOld el, String value) {
        int lengthVal = WebLocatorConfig.getMinCharsToType();
        int length = value.length();
//        el.getWebElement().clear();
        el.getWebElement().sendKeys(Keys.CONTROL, "a");
        el.getWebElement().sendKeys(Keys.DELETE);
        if (lengthVal == -1 || length <= lengthVal) {
            el.getWebElement().sendKeys(value);
            LOGGER.info("Set value({}): '{}'", el, getLogValue(el, value));
        } else {
            try {
                MultiThreadClipboardUtils.copyString(value);
            } catch (IllegalStateException e) {
                LOGGER.debug("IllegalStateException: cannot open system clipboard - try again.");
                MultiThreadClipboardUtils.copyString(value);
            }
//            MultiThreadClipboardUtils.pasteString(el);
            el.getWebElement().sendKeys(value.substring(length - 1));
            LOGGER.info("Paste value({}): string with size: '{}'", el, length);
        }
        return true;
    }

    private String getLogValue(WebLocatorOld el, String value) {
        String info = el.getXPathBuilder().getInfoMessage();
        if (Strings.isNullOrEmpty(info)) {
            info = el.getXPathBuilder().itemToString();
        }
        info = info.toLowerCase();

        return WebLocatorConfig.getLogParamsExclude().contains(info) ? "*****" : value;
    }

    @Override
    public String getCssValue(final WebLocatorOld el, final String propertyName) {
        return ensureExists(el) ? el.getWebElement().getCssValue(propertyName) : null;
    }

    @Override
    public String getTagName(final WebLocatorOld el) {
        return ensureExists(el) ? el.getWebElement().getTagName() : null;
    }

    @Override
    public String getAttribute(final WebLocatorOld el, final String attribute) {
        return ensureExists(el) ? getCurrentElementAttribute(el, attribute) : null;
    }

    public String getAttributeId(WebLocatorOld el) {
        String pathId = getAttribute(el, "id");
        if (el.hasId()) {
            final String id = el.getXPathBuilder().getId();
            if (!id.equals(pathId)) {
                LOGGER.warn("id is not same as pathId:{} - {}", id, pathId);
            }
            return id;
        }
        return pathId;
    }

    private boolean ensureExists(final WebLocatorOld el) {
        boolean present = el.currentElement != null || isElementPresent(el);
        if (!present) {
            LOGGER.debug("Element not found: {}", el);
        }
        return present;
    }

    @Override
    public String getCurrentElementAttribute(final WebLocatorOld el, final String attribute) {
        return RetryUtils.retrySafe(5, () -> {
            findAgain(el);
            return el.getWebElement().getAttribute(attribute);
        });
    }

    @Override
    public String getText(WebLocatorOld el) {
        return RetryUtils.retrySafe(6, () -> {
            findAgain(el);
            return el.getWebElement().getText();
        });
    }

    private String getSelector(WebLocatorOld el) {
        String css = el.getCssSelector();
        return Strings.isNullOrEmpty(css) ? el.getXPath() : css;
    }

    private boolean findAgain(WebLocatorOld el) {
        invalidateCache(el);
        return isElementPresent(el);
    }

    private void invalidateCache(WebLocatorOld el) {
        el.currentElement = null;
        el.setCurrentElementPath("");
    }

    @Override
    public String getValue(WebLocatorOld el) {
        return getAttribute(el, "value");
    }

    @Override
    public boolean isElementPresent(WebLocatorOld el) {
        findElement(el);
        return el.currentElement != null;
    }

    @Override
    public WebElement findElement(WebLocatorOld el) {
        final String path = getSelector(el);
//        if (isSamePath(el, path)) {
//            LOGGER.debug("currentElement already found one time: " + el);
        //return el.currentElement;
//        }
        doWaitElement(el, Duration.ZERO);
        el.setCurrentElementPath(path);
        return el.currentElement;
    }

    public List<WebElement> findElements(WebLocatorOld el) {
        return driver.findElements(el.getSelector());
    }

    @Override
    public WebElement waitElement(final WebLocatorOld el, final long millis, boolean showXPathLog) {
        return waitElement(el, Duration.ofMillis(millis), showXPathLog);
    }

    @Override
    public WebElement waitElement(final WebLocatorOld el, Duration duration, boolean showXPathLog) {
        doWaitElement(el, duration);
        if (el.currentElement == null && showXPathLog) {
            LOGGER.warn("Element not found after {} seconds; {}", duration.getSeconds(), el);
            logDetails(el);
        }
        return el.currentElement;
    }

    private void logDetails(WebLocatorOld el) {
        if (WebLocatorConfig.isLogXPath()) {
            LOGGER.debug("\t$x(\"" + el.getXPath() + "\")");
        }
        if (WebLocatorConfig.isLogSuggestions()) {
//            WebLocatorSuggestions.getSuggestion(el);
        }
    }

    private WebElement doWaitElement(final WebLocatorOld el, Duration duration) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(duration)
                .pollingEvery(Duration.ofMillis(1))
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotVisibleException.class)
                .ignoring(WebDriverException.class);

        try {
            if (el.getXPathBuilder().isVisibility()) {
                el.currentElement = wait.until(ExpectedConditions.visibilityOfElementLocated(el.getSelector()));
            } else {
                el.currentElement = wait.until(d -> d.findElement(el.getSelector()));
            }
        } catch (TimeoutException e) {
            el.currentElement = null;
        }
        return el.currentElement;
    }

    @Override
    public int size(WebLocatorOld el) {
        return findElements(el).size();
    }

    @Override
    public Point getLocation(WebLocatorOld el) {
        return ensureExists(el) ? el.getWebElement().getLocation() : null;
    }

    @Override
    public Dimension getSize(WebLocatorOld el) {
        return ensureExists(el) ? el.getWebElement().getSize() : null;
    }

    @Override
    public Rectangle getRect(WebLocatorOld el) {
        return ensureExists(el) ? el.getWebElement().getRect() : null;
    }

    @Override
    public boolean focus(WebLocatorOld el) {
        return fireEventWithJS(el, "mouseover") != null;
    }

    @Override
    public boolean mouseOver(WebLocatorOld el) {
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
    public boolean blur(WebLocatorOld el) {
        return fireEventWithJS(el, "blur") != null;
    }

    @Override
    public boolean isSelected(WebLocatorOld el) {
        return ensureExists(el) && el.getWebElement().isSelected();
    }

    public boolean isDisplayed(WebLocatorOld el) {
        return ensureExists(el) && el.getWebElement().isDisplayed();
    }

    public boolean isEnabled(WebLocatorOld el) {
        return ensureExists(el) && el.getWebElement().isEnabled();
    }

    public boolean isSamePath(WebLocatorOld el, String path) {
        return el.currentElement != null && (el.getCurrentElementPath().equals(path));
    }

    private boolean isCached(WebLocatorOld el) {
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

    public Object fireEventWithJS(WebLocatorOld el, String eventName) {
        String script = "if(document.createEvent){" +
                "var evObj = document.createEvent('MouseEvents');\n" +
                "evObj.initEvent('" + eventName + "', true, true);\n" +
                "return fireOnThis.dispatchEvent(evObj);\n" +
                "} else if(document.createEventObject) {" +
                "return fireOnThis.fireEvent('on" + eventName + "');" +
                "}";
        String id = getAttributeId(el);
        String cls;
        if (!Strings.isNullOrEmpty(id)) {
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
    public void highlight(WebLocatorOld el) {
        highlightElementWithDriver(el.currentElement);
    }

    public boolean download(String fileName, long timeoutMillis) {
        if (WebDriverConfig.isSilentDownload()) {
            if (WebDriverConfig.isHeadless() && WebDriverConfig.isChrome()) {
                Map<String, Object> commandParams = new HashMap<>();
                commandParams.put("cmd", "Page.setDownloadBehavior");
                Map<String, String> params = new HashMap<>();
                params.put("behavior", "allow");
                params.put("downloadPath", WebDriverConfig.getDownloadPath());
                commandParams.put("params", params);
                ObjectMapper objectMapper = new ObjectMapper();
                HttpClient httpClient = HttpClientBuilder.create().build();
                try {
                    String command = objectMapper.writeValueAsString(commandParams);
                    String uri = WebDriverConfig.getDriverService().getUrl().toString() + "/session/" + ((ChromeDriver) driver).getSessionId() + "/chromium/send_command";
                    HttpPost request = new HttpPost(uri);
                    request.addHeader("content-type", "application/json");
                    request.setEntity(new StringEntity(command));
                    httpClient.execute(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            fileName = WebDriverConfig.getDownloadPath() + File.separator + fileName;
            File file = new File(fileName);
            return FileUtils.waitFileIfIsEmpty(file, timeoutMillis) && fileName.equals(file.getAbsolutePath());
        } else {
            return RunExe.getInstance().download(fileName);
        }
    }

    public boolean browse(WebLocatorOld el) {
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