package com.sdl.bootstrap.tab;

import com.sdl.selenium.conditions.ConditionManager;
import com.sdl.selenium.conditions.RenderSuccessCondition;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tab extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Tab.class);

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
            returnPath = "//ul[@class='" + getBaseCls() + "' and count(.//li[" + isActive + "]//a[" + getItemPathText() + "]) > 0]";
        }
        return returnPath;
    }

    /**
     * this method return the path of the main Tab (that contains also this Tab/Panel)
     *
     * @return the path of the main TabPanel
     */
    private String getBaseTabPanelPath() {
        String selector = getBasePath();
        if (hasText()) {
            selector += (selector.length() > 0 ? " and " : "") + "count(.//li[@class='active']//a[" + getItemPathText() + "]) > 0";
        }
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
        boolean activated = new ConditionManager(200).add(new RenderSuccessCondition(new WebLocator(getContainer(), getTitlePath(true)))).execute().isSuccess();
        if (!activated) {
            WebLocator titleElement = new WebLocator(getContainer(), getTitlePath(false)).setInfoMessage(getText() + " Tab");
            activated = titleElement.click();
        }
        if (activated) {
            LOGGER.info("setActive : " + toString());
        }
        return activated;
    }
}
