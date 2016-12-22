package com.sdl.selenium.extjs6.tree;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.Row;
import com.sdl.selenium.web.table.Table;
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
            Table nodeEl = new Table(this).setClasses("x-grid-item").setChildNodes(textEl).setVisibility(true);
            Row row = nodeEl.getRow(1).setClasses("x-grid-row");
            WebLocator expanderEl = new WebLocator(nodeEl).setClasses("x-tree-expander");
            if (nodeEl.ready() && !(row.getAttributeClass().contains("x-grid-tree-node-expanded") || row.getAttributeClass().contains("x-grid-tree-node-leaf"))) {
                expanderEl.click();
            } else {
                WebLocator checkTree = new WebLocator(nodeEl).setClasses("x-tree-checkbox");
                WebLocator nodeTree = new WebLocator(nodeEl).setClasses("x-tree-node-text");
                selected = checkTree.isElementPresent() ? checkTree.click() : nodeTree.click();
            }
        }
        return selected;
    }
}
