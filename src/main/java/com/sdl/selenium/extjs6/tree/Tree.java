package com.sdl.selenium.extjs6.tree;

import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tree extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Tree.class);

    public Tree() {
        withClassName("Tree");
        withBaseCls("x-tree-panel");
    }

    public Tree(WebLocator container) {
        this();
        withContainer(container);
    }

    public boolean select(String... nodes) {
        boolean selected = false;
        for (String node : nodes) {
            WebLocator textEl = new WebLocator().setText(node);
            WebLocator nodeEl = new WebLocator(this).setClasses("x-grid-item").setChildNodes(textEl);
            WebLocator expanderEl = new WebLocator(nodeEl).setClasses("x-tree-expander");
            WebLocator expandedEl = new WebLocator(nodeEl).setClasses("x-tree-icon-parent-expanded");
            if (nodeEl.ready() && !expandedEl.isElementPresent() && expanderEl.isElementPresent()) {
                expanderEl.click();
            } else {
                WebLocator checkTree = new WebLocator(nodeEl).setClasses(" x-tree-checkbox");
                selected = checkTree.click();
            }
        }
        return selected;
    }
}
