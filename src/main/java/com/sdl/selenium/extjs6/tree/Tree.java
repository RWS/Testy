package com.sdl.selenium.extjs6.tree;

import com.sdl.selenium.extjs6.grid.Scrollable;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.Row;
import com.sdl.selenium.web.table.Table;

public class Tree extends WebLocator implements Scrollable {

    public Tree() {
        setClassName("Tree");
        setBaseCls("x-tree-panel");
    }

    public Tree(WebLocator container) {
        this();
        setContainer(container);
    }

    public boolean select(String... nodes) {
        boolean selected = false;
        for (String node : nodes) {
            WebLocator textEl = new WebLocator().setText(node);
            Table nodeEl = new Table(this).setClasses("x-grid-item").setChildNodes(textEl).setVisibility(true);
            scrollTo(nodeEl);
            Row row = nodeEl.getRow(1).setClasses("x-grid-row");
            WebLocator expanderEl = new WebLocator(nodeEl).setClasses("x-tree-expander");
            if (nodeEl.ready()) {
                String aClass = row.getAttributeClass();
                if (!(aClass.contains("x-grid-tree-node-expanded") || aClass.contains("x-grid-tree-node-leaf"))) {
                    expanderEl.click();
                } else {
                    WebLocator checkTree = new WebLocator(nodeEl).setClasses("x-tree-checkbox");
                    WebLocator nodeTree = new WebLocator(nodeEl).setClasses("x-tree-node-text");
                    selected = checkTree.isElementPresent() ? checkTree.click() : nodeTree.click();
                }
            }
        }
        return selected;
    }
}
