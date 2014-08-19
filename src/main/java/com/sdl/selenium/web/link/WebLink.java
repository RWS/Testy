package com.sdl.selenium.web.link;

import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

public class WebLink extends WebLocator {
    private static final Logger logger = Logger.getLogger(WebLink.class);
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
            boolean open = click();
            WebDriver driver = WebDriverConfig.getDriver();
            List<String> winList = new ArrayList<String>(driver.getWindowHandles());
            oldTab = winList.get(0);
            String newTab = winList.get(winList.size() - 1);
            return open && driver.switchTo().window(newTab) != null; // switch to new tab
        } catch (NoSuchWindowException e) {
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
}
