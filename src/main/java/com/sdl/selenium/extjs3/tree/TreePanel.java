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
        WebLocator node = new WebLocator(getParentNode()).setText(searchElement, SearchType.STARTS_WITH);
        LOGGER.info("Expanding the tree");
        return node.doubleClickAt();
    }

    public boolean select(String searchElement) {
        return select(searchElement, false);
    }

    public boolean select(String searchElement, Boolean startWith) {
        WebLocator node = new WebLocator(getParentNode()).setText(searchElement, startWith ? SearchType.STARTS_WITH : SearchType.EQUALS);
        LOGGER.info("Selecting the tree node");
        return node.click();
    }

    public String getStatus(String searchElement) {
        WebLocator node = new WebLocator(getParentNode()).setText(searchElement, SearchType.CONTAINS);
        String path = node.getPath() + "/following::*";
        WebLocator currentElement = new WebLocator(getPathBuilder().getContainer()).setElPath(path);
        if (currentElement.isElementPresent()) {
            return currentElement.getPathBuilder().getText();
        }
        return "INVALID";
    }

    public WebLocator getParentNode() {
        return new WebLocator(getPathBuilder().getContainer()).setClasses("x-tree-node-el");
    }
}
