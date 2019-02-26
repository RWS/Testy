package com.sdl.selenium.web.link;

import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.button.Button;
import org.openqa.selenium.WebDriver;

public class WebLink extends Button {

    private String oldTab;

    public WebLink() {
        setClassName("WebLink");
        setTag("a");
    }

    public WebLink(Locator container) {
        this();
        setContainer(container);
    }

    public WebLink(Locator container, String text, SearchType... searchTypes) {
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
