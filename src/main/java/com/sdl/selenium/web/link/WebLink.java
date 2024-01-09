package com.sdl.selenium.web.link;

import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebLink extends WebLocator {

    private String oldTab;

    public WebLink() {
        setClassName("WebLink");
        setTag("a");
    }

    public WebLink(WebLocator container) {
        this();
        setContainer(container);
    }

    public WebLink(WebElement webElement) {
        this();
        setWebElement(webElement);
    }

    public WebLink(WebLocator container, String text, SearchType... searchTypes) {
        this(container);
        setText(text, searchTypes);
    }

    public boolean openInNewWindow() {
        click();
        oldTab = null;
        if (WebDriverConfig.waitForNewTab(2, 1000)) {
            oldTab = WebDriverConfig.switchToLastTab();
        }
        return oldTab != null;
    }

    public boolean returnDefaultWindow() {
        WebDriver driver = WebDriverConfig.getDriver();
        if (oldTab != null) {
            driver.close();
        }
        WebDriverConfig.switchToFirstTab();
        return true;
    }
}
