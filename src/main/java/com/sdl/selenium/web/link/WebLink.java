package com.sdl.selenium.web.link;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.XPathBuilder;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class WebLink extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLink.class);
    
    private String oldTab;
    private XPathBuilder pathBuilder = getPathBuilder();

    public WebLink(By...bys) {
        pathBuilder.setTemplate("title", "@title='%s'");
        pathBuilder.init(bys);
        pathBuilder.defaults(By.tag("a"));
    }

    public WebLink(WebLocator container, By ...bys) {
        this(bys);
        pathBuilder.setContainer(container);
    }

    public WebLink(WebLocator container, String text) {
        this(container, By.text(text));
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
