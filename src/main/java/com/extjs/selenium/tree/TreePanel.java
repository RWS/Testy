package com.extjs.selenium.tree;

import com.extjs.selenium.ExtJsComponent;
import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.apache.log4j.Logger;

public class TreePanel extends ExtJsComponent {
    private static final Logger logger = Logger.getLogger(TreePanel.class);

    public TreePanel() {
        logger.warn("TreePanel is not implemented 100%");
        setClassName("TreePanel");
        //logger.debug(getClassName() + "() constructor");
    }

    public TreePanel(String cls){
        this();
        setCls(cls);
    }

    public boolean expand(String searchElement) {
        String path = "//*[contains(@class,'x-tree-node-el')]//*[starts-with(text(),'" + searchElement + "')]";
        if (new WebLocator(null, path).exists()) {
            logger.debug("Expanding the tree");
            if(hasWebDriver()){
                Actions builder = new Actions(driver);
                builder.doubleClick(driver.findElement(By.xpath(path))).build().perform();
            } else {
                selenium.doubleClick(path);
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
