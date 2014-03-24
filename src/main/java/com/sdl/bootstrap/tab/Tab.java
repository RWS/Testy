package com.sdl.bootstrap.tab;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Tab extends WebLocator {
    private static final Logger logger = Logger.getLogger(Tab.class);

    private Tab() {
        setClassName("Tab");
        setBaseCls("nav nav-tabs");
    }

    public Tab(String text) {
        this();
        setText(text);
        setSearchTextType(SearchType.EQUALS);
    }

    public Tab(WebLocator container, String text) {
        this(text);
        setContainer(container);
    }

    private String getTitlePath() {
        String returnPath = "";
        if (hasText()) {
            returnPath = "//ul[" + getBaseCls() + " and count(.//li[not(@class='active')]//a[" + Utils.fixPathSelector(getItemPathText()) + "]) > 0]";
        }
        return returnPath;
    }

    /**
     * this method return the path of the main Tab (that contains also this Tab/Panel)
     *
     * @return the path of the main TabPanel
     */
    public String getBaseTabPanelPath() {
        String selector = getBasePath();
        if (hasText()) {
            selector += " and count(.//li[@class='active']//a[" + Utils.fixPathSelector(getItemPathText()) + "]) > 0";
        }
        selector = Utils.fixPathSelector(selector);
        return "//ul[" + selector + "]";
    }

    /**
     * this method return the path of only one visible div from the main TabPanel
     *
     * @param disabled disabled
     * @return the path of only one visible div from the main TabPanel
     */
    @Override
    public String getItemPath(boolean disabled) {
        return getBaseTabPanelPath() + "//following-sibling::*[@class='tab-content']//*[@class='tab-pane active']";
    }

    /**
     * After the tab is set to active will wait 50ms to make sure tab is rendered
     *
     * @return true or false
     */
    public boolean setActive() {
//        String baseTabPath = "//*[" + Utils.fixPathSelector(getBasePath()) + "]";
//        String titlePath = baseTabPath + getTitlePath();
        WebLocator titleElement = new WebLocator(getContainer(), getTitlePath()).setInfoMessage(getText() + " Tab");
        logger.info("setActive : " + toString());
        boolean activated = titleElement.click();
        if (activated) {
            Utils.sleep(300); // need to make sure this tab is rendered
        }
        return activated;
    }

    public int getTabCount(String nameTab, String path) {
        List<WebElement> element = driver.findElements(By.xpath(path));
        int count = 0;
        for (WebElement el : element) {
            if (nameTab.equals(el.getText())) {
                logger.debug(count + " : " + el.getText());
                return count;
            }
            count++;
        }
        return -1;
    }
}
