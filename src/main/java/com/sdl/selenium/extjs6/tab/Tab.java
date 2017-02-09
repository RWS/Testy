package com.sdl.selenium.extjs6.tab;

import com.sdl.selenium.conditions.ConditionManager;
import com.sdl.selenium.conditions.RenderSuccessCondition;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.XPathBuilder;
import com.sdl.selenium.web.link.WebLink;
import com.sdl.selenium.web.tab.ITab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tab extends WebLocator implements ITab {
    private static final Logger LOGGER = LoggerFactory.getLogger(Tab.class);

    public Tab() {
        setClassName("TabPanel");
        setBaseCls("x-tab-bar");
        WebLink activeTab = new WebLink().setClasses("x-tab-active");
        setTemplateTitle(new WebLocator(activeTab));
    }

    public Tab(String title) {
        this();
        setTitle(title, SearchType.EQUALS, SearchType.DEEP_CHILD_NODE);
    }

    public Tab(WebLocator container) {
        this();
        setContainer(container);
    }

    public Tab(WebLocator container, String title) {
        this(title);
        setContainer(container);
    }

    private WebLocator getTitleInactiveEl() {
        WebLocator container = new WebLocator(getPathBuilder().getContainer()).setClasses(getPathBuilder().getBaseCls());
        return new WebLink(container).setText(getPathBuilder().getTitle(), SearchType.DEEP_CHILD_NODE, SearchType.EQUALS)
                .setInfoMessage(getPathBuilder().getTitle() + " Tab");
    }

    /**
     * this method return the path of the main Tab (that contains also this Tab/Panel)
     *
     * @return the path of the main TabPanel
     */
    private String getBaseTabPanelPath() {
        String selector = getPathBuilder().getBasePath();
        return getPathBuilder().getRoot() + getPathBuilder().getTag() + "[" + selector + "]";
    }

    protected XPathBuilder createXPathBuilder() {
        return new XPathBuilder() {
            /**
             * this method return the path of only one visible div from the main TabPanel
             * @param disabled disabled
             * @return the path of only one visible div from the main TabPanel
             */
            @Override
            public String getItemPath(boolean disabled) {
                WebLocator body = new WebLocator().setTag("following-sibling::*").setClasses("x-panel-body");
                WebLocator tab = new WebLocator(body).setRoot("/").setExcludeClasses("x-hidden-offsets").setClasses("x-tabpanel-child");
                return getBaseTabPanelPath() + tab.getXPath();
            }
        };
    }

    /**
     * After the tab is set to active
     *
     * @return true or false
     */
    @Override
    public boolean setActive() {
        WebLocator inactiveTab = getTitleInactiveEl().setExcludeClasses("x-tab-active");
        boolean activated = isActive() || inactiveTab.click();
        if (activated) {
            LOGGER.info("setActive : " + toString());
        }
        return activated;
    }

    @Override
    public boolean isActive() {
        return new ConditionManager(200).add(new RenderSuccessCondition(this)).execute().isSuccess();
    }

    public boolean isTabDisplayed() {
        return getTitleInactiveEl().ready();
    }

    public boolean close() {
        WebLocator titleEl = getTitleInactiveEl().setClasses("x-tab-active");
        WebLocator closeEl = new WebLocator(titleEl).setClasses("x-tab-close-btn");
        return closeEl.click();
    }
}
