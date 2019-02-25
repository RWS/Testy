package com.sdl.selenium.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.utils.config.WebLocatorConfig;
import com.sdl.selenium.web.utils.FileUtils;
import com.sdl.selenium.web.utils.MultiThreadClipboardUtils;
import com.sdl.selenium.web.utils.RetryUtils;
import lombok.extern.slf4j.Slf4j;
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

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class NewWebLocatorExecutor {

    private WebDriver driver;

    public NewWebLocatorExecutor(WebDriver driver) {
        this.driver = driver;
    }

    public boolean click(Locator el) {
//        if (highlight) {
//            doHighlight();
//        }
        Boolean click = RetryUtils.retrySafe(6, () -> {
            findAgain(el);
            el.getWebElement().click();
            return el.getWebElement() != null;
        });
        return click == null ? false : click;
    }

    public boolean clickAt(Locator el) {
        focus(el);
        return click(el);
    }

    public boolean doubleClickAt(Locator el) {
        boolean clicked = false;
        if (ensureExists(el)) {
            try {
                Actions builder = new Actions(driver);
                builder.doubleClick(el.getWebElement()).perform();
                clicked = true;
            } catch (Exception e) {
                // http://code.google.com/p/selenium/issues/detail?id=244
                log.info("Exception in doubleClickAt {}", e);
                clicked = fireEventWithJS(el, "dblclick") != null;
            }
        }
        return clicked;
    }

    public boolean submit(Locator el) {
        Boolean submit = RetryUtils.retry(6, () -> {
            findAgain(el);
            el.getWebElement().submit();
            return el.getWebElement() != null;
        });
        return submit == null ? false : submit;
    }

    public boolean clear(Locator el) {
        Boolean clear = RetryUtils.retry(6, () -> {
            findAgain(el);
            el.getWebElement().clear();
            return el.getWebElement() != null;
        });
        return clear == null ? false : clear;
    }

    public boolean sendKeys(Locator el, CharSequence... charSequences) {
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

    private boolean tryAgainDoSendKeys(Locator el, CharSequence... charSequences) {
        if (findAgain(el)) {
            el.getWebElement().sendKeys(charSequences); // not sure it will click now
            return true;
        } else {
            log.error("currentElement is null after to try currentElement: {}", el);
            return false;
        }
    }

    public boolean setValue(Locator el, String value) {
        Boolean retry = RetryUtils.retry(6, () -> doSetValue(el, value));
        return retry == null ? false : retry;
    }

    private boolean doSetValue(Locator el, String value) {
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
                log.debug("IllegalStateException: cannot open system clipboard - try again.");
                MultiThreadClipboardUtils.copyString(value);
            }
//            MultiThreadClipboardUtils.pasteString(el);
            el.getWebElement().sendKeys(value.substring(length - 1));
            log.info("Paste value({}): string with size: '{}'", el, length);
        }
        return true;
    }

    private String getLogValue(Locator el, String value) {
        String info = el.getXPathBuilder().getInfoMessage();
        if (Strings.isNullOrEmpty(info)) {
            info = el.getXPathBuilder().itemToString();
        }
        info = info.toLowerCase();

        return WebLocatorConfig.getLogParamsExclude().contains(info) ? "*****" : value;
    }

    public String getCssValue(final Locator el, final String propertyName) {
        return ensureExists(el) ? el.getWebElement().getCssValue(propertyName) : null;
    }

    public String getTagName(final Locator el) {
        return ensureExists(el) ? el.getWebElement().getTagName() : null;
    }

    public String getAttribute(final Locator el, final String attribute) {
        return ensureExists(el) ? getCurrentElementAttribute(el, attribute) : null;
    }

    public String getAttributeId(Locator el) {
        return getAttribute(el, "id");
    }

    private boolean ensureExists(final Locator el) {
        boolean present = el != null || isElementPresent(el);
        if (!present) {
            log.debug("Element not found: {}", el);
        }
        return present;
    }

    public String getCurrentElementAttribute(final Locator el, final String attribute) {
        return RetryUtils.retrySafe(5, () -> {
            findAgain(el);
            return el.getWebElement().getAttribute(attribute);
        });
    }

    public String getText(Locator el) {
        return RetryUtils.retrySafe(6, () -> {
            findAgain(el);
            return el.getWebElement().getText();
        });
    }

    private String getSelector(Locator el) {
        String css = el.getXPathBuilder().getCssSelector();
        return Strings.isNullOrEmpty(css) ? el.getXPath() : css;
    }

    private boolean findAgain(Locator el) {
//        invalidateCache(el);
        return isElementPresent(el);
    }

//    private void invalidateCache(Locator el) {
//        el = null;
//        el.setCurrentElementPath("");
//    }

    public String getValue(Locator el) {
        return getAttribute(el, "value");
    }

    public boolean isElementPresent(Locator el) {
        findElement(el);
        return el != null;
    }

    public WebElement findElement(Locator el) {
        return doWaitElement(el, Duration.ZERO);
    }

    public List<WebElement> findElements(Locator el) {
        return driver.findElements(el.getSelector());
    }

    public WebElement waitElement(final Locator el, final long millis, boolean showXPathLog) {
        return waitElement(el, Duration.ofMillis(millis), showXPathLog);
    }

    public WebElement waitElement(final Locator el, Duration duration, boolean showXPathLog) {
        doWaitElement(el, duration);
        if (el.getWebElement() == null && showXPathLog) {
            log.warn("Element not found after {} seconds; {}", duration.getSeconds(), el);
            logDetails(el);
        }
        return el.getWebElement();
    }

    private void logDetails(Locator el) {
        if (WebLocatorConfig.isLogXPath()) {
            log.debug("\t$x(\"" + el.getXPath() + "\")");
        }
//        if (WebLocatorConfig.isLogSuggestions()) {
//            WebLocatorSuggestions.getSuggestion(el);
//        }
    }

    private WebElement doWaitElement(Locator el, Duration duration) {
        WebElement webElement;
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(duration)
                .pollingEvery(Duration.ofMillis(1))
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotVisibleException.class)
                .ignoring(WebDriverException.class);
        try {
            if (el.getXPathBuilder().isVisibility()) {
                webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(el.getSelector()));
            } else {
                final Locator finalEl = el;
                webElement = wait.until(d -> d.findElement(finalEl.getSelector()));
            }
        } catch (TimeoutException e) {
            webElement = null;
        }
        return webElement;
    }

    public int size(Locator el) {
        return findElements(el).size();
    }

    public Point getLocation(Locator el) {
        return ensureExists(el) ? el.getWebElement().getLocation() : null;
    }

    public Dimension getSize(Locator el) {
        return ensureExists(el) ? el.getWebElement().getSize() : null;
    }

    public Rectangle getRect(Locator el) {
        return ensureExists(el) ? el.getWebElement().getRect() : null;
    }

    public boolean focus(Locator el) {
        return fireEventWithJS(el, "mouseover") != null;
    }

    public boolean mouseOver(Locator el) {
        boolean mouseOver;
        try {
            if (ensureExists(el)) {
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

    public boolean blur(Locator el) {
        return fireEventWithJS(el, "blur") != null;
    }

    public boolean isSelected(Locator el) {
        return ensureExists(el) && el.getWebElement().isSelected();
    }

    public boolean isDisplayed(Locator el) {
        return ensureExists(el) && el.getWebElement().isDisplayed();
    }

    public boolean isEnabled(Locator el) {
        return ensureExists(el) && el.getWebElement().isEnabled();
    }

//    public boolean isSamePath(Locator el, String path) {
//        return el != null && (el.getCurrentElementPath().equals(path));
//    }

//    private boolean isCached(Locator el) {
//        boolean cached = false; // TODO config
//        return cached;
//    }

    public Object executeScript(String script, Object... objects) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        try {
            return javascriptExecutor.executeScript(script, objects);
        } catch (WebDriverException e) {
            log.error("WebDriverException in executeScript: " + script, e);
            return null;
        }
    }

    public Object fireEventWithJS(Locator el, String eventName) {
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

//    public void highlight(Locator el) {
//        highlightElementWithDriver(el);
//    }

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

    public boolean browse(Locator el) {
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

//    private void highlightElementWithDriver(Locator el) {

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
//    }
}