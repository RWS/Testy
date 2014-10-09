package com.extjs.selenium.tree;

import com.extjs.selenium.ExtJsComponent;
import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TreePanel extends ExtJsComponent {
    private static final Logger logger = LoggerFactory.getLogger(TreePanel.class);

    public TreePanel() {
        logger.warn("TreePanel is not implemented 100%");
        setClassName("TreePanel");
    }

    public TreePanel(String cls){
        this();
        setClasses(cls);
    }

    public TreePanel(WebLocator container){
        this();
        setContainer(container);
    }

    public boolean expand(String searchElement) {
        String path = "//*[contains(@class,'x-tree-node-el')]//*[starts-with(text(),'" + searchElement + "')]";
        if (new WebLocator(null, path).exists()) {
            logger.debug("Expanding the tree");
            if(WebDriverConfig.hasWebDriver()){
                WebDriver driver = WebDriverConfig.getDriver();
                Actions builder = new Actions(driver);
                builder.doubleClick(driver.findElement(By.xpath(path))).build().perform();
            } else {
                WebDriverConfig.getSelenium().doubleClick(path);
            }
            return true;
        }
        return false;
    }

    public boolean select(String searchElement) {
        return select(searchElement, false);
    }

    public boolean select(String searchElement, Boolean startWith) {
        String path = "//*[contains(@class,'x-tree-node-el')]//*" +
                (startWith ? "[starts-with(text(),'" + searchElement + "')]" : "[text()='" + searchElement + "']");
        WebLocator currentElement = new WebLocator(getContainer(), path);
        if (currentElement.isElementPresent()) {
            logger.debug("Selecting the tree node");
            return currentElement.click();
        }
        return false;
    }

    public String getStatus(String searchElement) {
        String path = "//*[contains(@class,'x-tree-node-el')]//*[contains(text(),'" + searchElement + "')]/following::*";
        WebLocator currentElement = new WebLocator(getContainer(), path);
        if (currentElement.isElementPresent()) {
            return currentElement.getText();
        }
        return "INVALID";
    }
}
