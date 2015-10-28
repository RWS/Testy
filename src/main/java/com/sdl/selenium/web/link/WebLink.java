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
    private boolean isNewTab;

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
        assertClick();
        oldTab = null;
        if(WebDriverConfig.waitForNewTab(2, 1000)) {
            oldTab = WebDriverConfig.switchToLastTab();
        }
        return oldTab != null;
    }

    public boolean returnDefaultWindow() {
        WebDriver driver = WebDriverConfig.getDriver();
        if(oldTab != null) {
            driver.close();
        }
        WebDriverConfig.switchToFirstTab();
        return true;
    }
}
