package com.sdl.selenium.bootstrap.tab;

import com.sdl.selenium.conditions.ConditionManager;
import com.sdl.selenium.conditions.RenderSuccessCondition;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.link.WebLink;
import com.sdl.selenium.web.tab.ITab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class Tab extends WebLocator implements ITab {
    private static final Logger LOGGER = LoggerFactory.getLogger(Tab.class);

    private Tab() {
        setClassName("Tab");
        setBaseCls("nav nav-tabs");
        setTag("ul");
        setTemplate("text", "count(.//li[@class='active']//a[.=%s]) > 0]//following-sibling::*[@class='tab-content']//*[@class='tab-pane active'");
    }

    public Tab(String title) {
        this();
        setText(title, SearchType.EQUALS);
    }

    public Tab(WebLocator container, String text) {
        this(text);
        setContainer(container);
    }

    private String getTitlePath(boolean active) {
        WebLink link = new WebLink().setText(getPathBuilder().getText(), SearchType.EQUALS);
        String isActive = active ? "@class='active'" : "not(@class='active')";
        WebLocator el = new WebLocator().setTag("ul").setClasses(getPathBuilder().getBaseCls());
        return el.getXPath() + "//li[" + isActive + "]" + link.getXPath();
    }

    /**
     * After the tab is set to active
     *
     * @return true or false
     */
    @Override
    public boolean setActive() {
        boolean activated = isActive();
        if (!activated) {
            WebLocator locator1 = new WebLocator(getPathBuilder().getContainer()).setElPath(getTitlePath(false));
            WebLocator titleElement = locator1.setInfoMessage(getPathBuilder().getText() + " Tab");
            activated = titleElement.click();
        }
        if (activated) {
            LOGGER.info("setActive : " + toString());
        }
        return activated;
    }

    @Override
    public boolean isActive() {
        WebLocator locator = new WebLocator(getPathBuilder().getContainer()).setElPath(getTitlePath(true));
        return new ConditionManager(Duration.ofMillis(200)).add(new RenderSuccessCondition(locator)).execute().isSuccess();
    }
}