package com.sdl.selenium.bootstrap.tab;

import com.sdl.selenium.conditions.ConditionManager;
import com.sdl.selenium.conditions.RenderSuccessCondition;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.tab.ITab;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

@Slf4j
public class Tab extends WebLocator implements ITab {

    private Tab() {
        setClassName("Tab");
        setBaseCls("nav-tabs");
        setTag("ul");
        WebLocator liEl = new WebLocator().setTag("li").setBaseCls("active");
        setTemplateTitle(liEl);
        WebLocator tabContent = new WebLocator().setTag("following-sibling::*").setClasses("tab-content");
        WebLocator tabPanel = new WebLocator(tabContent).setClasses("tab-pane", "active");
        setFinalXPath(tabPanel.getXPath());
    }

    public Tab(String title, SearchType... searchTypes) {
        this();
        if (searchTypes.length == 0) {
            searchTypes = new SearchType[]{SearchType.EQUALS};
        }
        setTitle(title, searchTypes);
    }

    public Tab(WebLocator container, String text, SearchType... searchTypes) {
        this(text, searchTypes);
        setContainer(container);
    }

    public WebLocator getTitlePath(boolean active) {
        WebLocator titleEl = getPathBuilder().getTemplateTitle().get("title");
        final WebLocator titleElFinal = new WebLocator().setBaseCls(titleEl.getPathBuilder().getBaseCls()).setTag(titleEl.getPathBuilder().getTag());
        String title = getPathBuilder().getTitle();
        List<SearchType> searchTitleType = getPathBuilder().getSearchTitleType();
        titleElFinal.setText(title, searchTitleType.toArray(SearchType[]::new));
        if (!active) {
            titleElFinal.setBaseCls(null);
        }
        titleElFinal.setContainer(getPathBuilder().getContainer());
        return active ? new WebLocator(titleElFinal).setElPath(getPathBuilder().getFinalXPath()) : titleElFinal;
    }

    @Override
    public boolean setActive() {
        boolean activated = isActive();
        if (!activated) {
            WebLocator titleElement = getTitlePath(false).setInfoMessage(getPathBuilder().getTitle() + " Tab");
            activated = titleElement.click();
        }
        if (activated) {
            log.info("setActive: {}", this);
        }
        return activated;
    }

    @Override
    public boolean isActive() {
        WebLocator titleActive = getTitlePath(true);
        return new ConditionManager(Duration.ofMillis(200)).add(new RenderSuccessCondition(titleActive)).execute().isSuccess();
    }
}