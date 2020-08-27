package com.sdl.selenium.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.sdl.selenium.WebLocatorSuggestions;
import com.sdl.selenium.WebLocatorUtils;
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

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebLocatorDriverExecutor implements WebLocatorExecutor {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(WebLocatorDriverExecutor.class);
    private WebDriver driver;

    public WebLocatorDriverExecutor(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public boolean click(IWebLocator el) {
        invalidateCache(el);
        boolean click = false;
        if (!el.getCurrentElementPath().equals(getSelector(el))) {
            click = RetryUtils.retryRunnableSafe(1, () -> el.getWebElement().click());
        }
        if (!click) {
            click = RetryUtils.retryRunnableSafe(4, () -> {
                findAgain(el);
                el.getWebElement().click();
            });
        }
        return click;
    }

    @Override
    public boolean clickAt(IWebLocator el) {
        focus(el);
        return click(el);
    }

    @Override
    public boolean doubleClickAt(IWebLocator el) {
        invalidateCache(el);
        boolean clicked = false;
        if (findAgain(el)) {
            try {
                Actions builder = new Actions(driver);
                builder.doubleClick(el.getWebElement()).perform();
                clicked = true;
            } catch (Exception e) {
                // http://code.google.com/p/selenium/issues/detail?id=244
//                log.warn("Exception in doubleClickAt {}", e);
                clicked = fireEventWithJS(el, "dblclick") != null;
            }
        }
        return clicked;
    }

    public boolean submit(IWebLocator el) {
        invalidateCache(el);
        boolean submit = false;
        if (!el.getCurrentElementPath().equals(getSelector(el))) {
            submit = RetryUtils.retryRunnableSafe(1, () -> el.getWebElement().submit());
        }
        if (!submit) {
            submit = RetryUtils.retryRunnableSafe(4, () -> {
                findAgain(el);
                el.getWebElement().submit();
            });
        }
        return submit;
    }

    @Override
    public boolean clear(IWebLocator el) {
        invalidateCache(el);
        boolean clear = false;
        if (!el.getCurrentElementPath().equals(getSelector(el))) {
            clear = RetryUtils.retryRunnableSafe(1, () -> el.getWebElement().clear());
        }
        if (!clear) {
            clear = RetryUtils.retryRunnableSafe(4, () -> {
                findAgain(el);
                el.getWebElement().clear();
            });
        }
        return clear;
    }

    @Override
    public boolean sendKeys(IWebLocator el, java.lang.CharSequence... charSequences) {
        invalidateCache(el);
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
                        log.error("final ElementNotVisibleException in sendKeys: {}", el, exc);
                        throw exc;
                    }
                }
            } catch (WebDriverException e) {
                //TODO this fix is for Chrome
                Actions builder = new Actions(driver);
                builder.click(el.getWebElement());
                builder.sendKeys(charSequences);
                sendKeys = true;
            }
        }
        return sendKeys;
    }

    private boolean tryAgainDoSendKeys(IWebLocator el, java.lang.CharSequence... charSequences) {
        if (findAgain(el)) {
            el.getWebElement().sendKeys(charSequences); // not sure it will click now
            return true;
        } else {
            log.error("currentElement is null after to try currentElement: {}", el);
            return false;
        }
    }

    @Override
    public boolean setValue(IWebLocator el, String value) {
        return doSetValue(el, value);
    }

    private boolean doSetValue(IWebLocator el, String value) {
        invalidateCache(el);
        int lengthVal = WebLocatorConfig.getMinCharsToType();
        int length = value.length();
//        el.getWebElement().clear();
        el.getWebElement().sendKeys(Keys.CONTROL, "a");
        el.getWebElement().sendKeys(Keys.DELETE);
        if (lengthVal == -1 || length <= lengthVal) {
            el.getWebElement().sendKeys(value);
            log.info("Set value({}): '{}'", el, getLogValue(el, value));
        } else {
            try {
                MultiThreadClipboardUtils.copyString(value);
            } catch (IllegalStateException e) {
                log.warn("IllegalStateException: cannot open system clipboard - try again.");
                MultiThreadClipboardUtils.copyString(value);
            }
            MultiThreadClipboardUtils.pasteString(el.getLocator());
            el.getWebElement().sendKeys(value.substring(length - 1));
            log.info("Paste value({}): string with size: '{}'", el, length);
        }
        return true;
    }

    private String getLogValue(IWebLocator el, String value) {
        String info = el.getLocator().getPathBuilder().getInfoMessage();
        if (Strings.isNullOrEmpty(info)) {
            info = el.getLocator().getPathBuilder().itemToString();
        }
        info = info.toLowerCase();

        return WebLocatorConfig.getLogParamsExclude().contains(info) ? "*****" : value;
    }

    @Override
    public String getCssValue(final IWebLocator el, final String propertyName) {
        invalidateCache(el);
        String cssValue = null;
        if (!el.getCurrentElementPath().equals(getSelector(el))) {
            cssValue = RetryUtils.retrySafe(1, () -> el.getWebElement().getCssValue(propertyName));
        }
        if (cssValue == null) {
            return RetryUtils.retrySafe(4, () -> {
                findAgain(el);
                return el.getWebElement().getCssValue(propertyName);
            });
        }
        return cssValue;
    }

    @Override
    public String getTagName(final IWebLocator el) {
        invalidateCache(el);
        String tagName = null;
        if (!el.getCurrentElementPath().equals(getSelector(el))) {
            tagName = RetryUtils.retrySafe(1, () -> el.getWebElement().getTagName());
        }
        if (tagName == null) {
            return RetryUtils.retrySafe(4, () -> {
                findAgain(el);
                return el.getWebElement().getTagName();
            });
        }
        return tagName;
    }

    @Override
    public String getAttribute(final IWebLocator el, final String attribute) {
        return getAttribute(el, attribute, false);
    }

    @Override
    public String getAttribute(final IWebLocator el, final String attribute, boolean instant) {
        invalidateCache(el);
        String attributeValue = null;
        if (!el.getCurrentElementPath().equals(getSelector(el))) {
            attributeValue = RetryUtils.retrySafe(1, () -> el.getWebElement().getAttribute(attribute));
        }
        if (!instant) {
            if (attributeValue == null) {
                return RetryUtils.retrySafe(4, () -> {
                    findAgain(el);
                    return el.getWebElement().getAttribute(attribute);
                });
            }
        }
        return attributeValue;
    }

    public String getAttributeId(IWebLocator el) {
        String id = getAttribute(el, "id");
        if (el.getLocator().hasId()) {
            final String pathId = el.getLocator().getPathBuilder().getId();
            if (!id.equals(pathId)) {
                log.warn("id is not same as pathId:{} - {}", id, pathId);
            }
            return id;
        }
        return id;
    }

    private boolean ensureExists(final IWebLocator el) {
        boolean present = el.getWebElement() != null || isElementPresent(el);
        if (!present) {
            log.debug("Element not found: {}", el);
        }
        return present;
    }

    @Override
    public String getText(IWebLocator el) {
        invalidateCache(el);
        String text = null;
        if (!el.getCurrentElementPath().equals(getSelector(el))) {
            text = RetryUtils.retrySafe(1, () -> el.getWebElement().getText());
        }
        if (text == null) {
            return RetryUtils.retrySafe(4, () -> {
                findAgain(el);
                return el.getWebElement().getText();
            });
        }
        return text;
    }

    private String getSelector(IWebLocator el) {
        String css = el.getLocator().getCssSelector();
        return Strings.isNullOrEmpty(css) ? el.getXPath() : css;
    }

    private boolean findAgain(IWebLocator el) {
        invalidateCache(el);
        return isElementPresent(el);
    }

    private void invalidateCache(IWebLocator el) {
//        el.setWebElement();
        el.setCurrentElementPath("");
    }

    @Override
    public String getValue(IWebLocator el) {
        return getAttribute(el, "value");
    }

    @Override
    @Deprecated
    public boolean isElementPresent(IWebLocator el) {
        return findElement(el) != null;
    }

    @Override
    public boolean isPresent(IWebLocator el) {
        return findElement(el) != null;
    }

    @Override
    public WebElement findElement(IWebLocator el) {
        final String path = getSelector(el);
//        if (isSamePath(el, path)) {
//            log.debug("currentElement already found one time: " + el);
        //return el.getWebElement();
//        }
        WebElement webElement = doWaitElement(el, Duration.ZERO);
        el.setCurrentElementPath(path);
        return webElement;
    }

    public List<WebElement> findElements(IWebLocator el) {
        return driver.findElements(el.getLocator().getSelector());
    }

    @Override
    @Deprecated
    public WebElement waitElement(final IWebLocator el, final long millis, boolean showXPathLog) {
        return waitElement(el, Duration.ofMillis(millis), showXPathLog);
    }

    @Override
    public WebElement waitElement(final IWebLocator el, Duration duration, boolean showXPathLog) {
        WebElement webElement = doWaitElement(el, duration);
        if (webElement == null && showXPathLog) {
            log.warn("Element not found after {} seconds; {}", duration.getSeconds(), el.toString());
            logDetails(el);
        }
        return webElement;
    }

    private <T extends WebLocatorAbstractBuilder> void logDetails(IWebLocator el) {
        if (WebLocatorConfig.isLogXPath()) {
            log.debug("\t" + WebLocatorUtils.getFirebugXPath(el));
        }
        if (WebLocatorConfig.isLogSuggestions()) {
            WebLocatorSuggestions.getSuggestion(el);
        }
    }

    private WebElement doWaitElement(final IWebLocator el, Duration duration) {
        WebElement webElement;
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(duration)
                .pollingEvery(Duration.ofMillis(1))
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotVisibleException.class)
                .ignoring(WebDriverException.class);
        try {
            if (el.getLocator().getPathBuilder().isVisibility()) {
                webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(el.getLocator().getSelector()));
            } else {
                webElement = wait.until(d -> d.findElement(el.getLocator().getSelector()));
            }
        } catch (TimeoutException e) {
            webElement = null;
        }
        return webElement;
    }

    @Override
    public int size(IWebLocator el) {
        return findElements(el).size();
    }

    @Override
    public Point getLocation(IWebLocator el) {
        return ensureExists(el) ? el.getWebElement().getLocation() : null;
    }

    @Override
    public Dimension getSize(IWebLocator el) {
        return ensureExists(el) ? el.getWebElement().getSize() : null;
    }

    @Override
    public Rectangle getRect(IWebLocator el) {
        return ensureExists(el) ? el.getWebElement().getRect() : null;
    }

    @Override
    public boolean focus(IWebLocator el) {
        return fireEventWithJS(el, "mouseover") != null;
    }

    @Override
    public boolean mouseOver(IWebLocator el) {
        boolean mouseOver;
        try {
            if (findAgain(el)) {
                Actions builder = new Actions(driver);
                builder.moveToElement(el.getWebElement()).perform();
                mouseOver = true;
            } else {
                mouseOver = false;
            }
        } catch (WebDriverException e) {
            log.error("Could not mouse over {}, {}", el, e);
            mouseOver = false;
        }
        return mouseOver;
    }

    @Override
    public boolean blur(IWebLocator el) {
        return fireEventWithJS(el, "blur") != null;
    }

    @Override
    public boolean isSelected(IWebLocator el) {
        return findAgain(el) && el.getWebElement().isSelected();
    }

    public boolean isDisplayed(IWebLocator el) {
        return findAgain(el) && el.getWebElement().isDisplayed();
    }

    public boolean isEnabled(IWebLocator el) {
        return findAgain(el) && el.getWebElement().isEnabled();
    }

    public boolean isSamePath(IWebLocator el, String path) {
        return el.getWebElement() != null && (el.getCurrentElementPath().equals(path));
    }

    private boolean isCached(IWebLocator el) {
        boolean cached = false; // TODO config
        return cached;
    }

    @Override
    public Object executeScript(String script, Object... objects) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        try {
            return javascriptExecutor.executeScript(script, objects);
        } catch (WebDriverException e) {
            log.error("WebDriverException in executeScript: " + script, e);
            return null;
        }
    }

    public Object fireEventWithJS(IWebLocator el, String eventName) {
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
    public void highlight(IWebLocator el) {
        highlightElementWithDriver(el.getWebElement());
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

    public boolean browse(IWebLocator el) {
        try {
            focus(el);
            Actions builder = new Actions(driver);
            builder.moveToElement(el.getWebElement()).perform();
            builder.click().perform();
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
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