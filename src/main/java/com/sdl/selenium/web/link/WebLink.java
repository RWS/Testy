package com.sdl.selenium.web.link;

import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WebLink extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLink.class);

    private String oldTab;

    public WebLink() {
        setClassName("WebLink");
        setTag("a");
    }

    public WebLink(WebLocator container) {
        this();
        setContainer(container);
    }

    public WebLink(WebLocator container, String text) {
        this(container);
        setText(text);
    }

    public boolean openInNewWindow() {
        try {
            WebDriver driver = WebDriverConfig.getDriver();
            oldTab = driver.getWindowHandle();
            boolean open = this.click();
            waitForNewTab(driver, 5);
            List<String> winList = new ArrayList<>(driver.getWindowHandles());
            String newTab = winList.get(winList.size() - 1);
            return open && driver.switchTo().window(newTab) != null; // switch to new tab
        } catch (NoSuchWindowException e) {
            LOGGER.debug("NoSuchWindowException {}", e);
            return false;
        }
    }

    public boolean returnDefaultWindow() {
        try {
            WebDriver driver = WebDriverConfig.getDriver();
            driver.close();
            return driver.switchTo().window(oldTab) != null;
        } catch (NoSuchWindowException e) {
            return false;
        }
    }

    /**
     * @param driver  : this is webdriver
     * @param timeout : time you define to wait the tab open
     * @return true if tab open in the time, false if tab not open in the time.
     */
    private boolean waitForNewTab(WebDriver driver, int timeout) {
        boolean check = false;
        int count = 0;
        while (!check && count < timeout) {
            LOGGER.debug("Waiting... " + count);
            Utils.sleep(100);
            Set<String> winHandle = driver.getWindowHandles();
            if (winHandle.size() > 1) {
                check = true;
            }
            count++;
        }
        return check;
    }
}
