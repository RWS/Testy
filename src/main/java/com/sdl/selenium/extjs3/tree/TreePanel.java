package com.sdl.selenium.extjs3.tree;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TreePanel extends ExtJsComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(TreePanel.class);

    public TreePanel() {
        LOGGER.warn("TreePanel is not implemented 100%");
        withClassName("TreePanel");
    }

    public TreePanel(String cls){
        this();
        withClasses(cls);
    }

    public TreePanel(WebLocator container){
        this();
        withContainer(container);
    }

    public boolean expand(String searchElement) {
        WebLocator node = new WebLocator(getParentNode()).withText(searchElement, SearchType.STARTS_WITH);
        LOGGER.info("Expanding the tree");
        return node.doubleClickAt();
    }

    public boolean select(String searchElement) {
        return select(searchElement, false);
    }

    public boolean select(String searchElement, Boolean startWith) {
        WebLocator node = new WebLocator(getParentNode()).withText(searchElement, startWith ? SearchType.STARTS_WITH : SearchType.EQUALS);
        LOGGER.info("Selecting the tree node");
        return node.doClick();
    }

    public String getStatus(String searchElement) {
        WebLocator node = new WebLocator(getParentNode()).withText(searchElement, SearchType.CONTAINS);
        String path = node.getXPath() + "/following::*";
        WebLocator currentElement = new WebLocator(getPathBuilder().getContainer()).withElxPath(path);
        if (currentElement.isElementPresent()) {
            return currentElement.getPathBuilder().getText();
        }
        return "INVALID";
    }

    public WebLocator getParentNode() {
        return new WebLocator(getPathBuilder().getContainer()).withClasses("x-tree-node-el");
    }
}
