package com.sdl.bootstrap.tab;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

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

    private String getTitlePath(boolean active) {
        String returnPath = "";
        if (hasText()) {
            String isActive = active ? "@class='active'" : "not(@class='active')";
            returnPath = "//ul[" + getBaseCls() + " and count(.//li[" + isActive + "]//a[" + Utils.fixPathSelector(getItemPathText()) + "]) > 0]";
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
     * After the tab is set to active
     *
     * @return true or false
     */
    public boolean setActive() {
        boolean activated = new WebLocator(getContainer(), getTitlePath(true)).ready();
        if (!activated) {
            WebLocator titleElement = new WebLocator(getContainer(), getTitlePath(false)).setInfoMessage(getText() + " Tab");
            activated = titleElement.click();
        }
        if (activated) {
            logger.info("setActive : " + toString());
        }
        return activated;
    }
}
