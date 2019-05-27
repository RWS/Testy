package com.sdl.selenium.extjs6.tree;

import com.sdl.selenium.extjs6.grid.Scrollable;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.Row;
import com.sdl.selenium.web.table.Table;
import org.openqa.selenium.WebDriverException;

public class TreeGrid extends WebLocator implements Scrollable {

    public TreeGrid() {
        setClassName("TreeGrid");
        setBaseCls("x-tree-panel");
    }

    public TreeGrid(WebLocator container) {
        this();
        setContainer(container);
    }

    public boolean select(String... nodes) {
        return select(false, nodes);
    }

    public boolean select(boolean doScroll, String... nodes) {
        if (doScroll) {
            scrollTop();
        }
        boolean selected = false;
        String parent = null;
        for (String node : nodes) {
            WebLocator textEl = new WebLocator().setText(node);
            Table nodeSelected = new Table(this).setClasses("x-grid-item", "x-grid-item-selected");
            Row rowSelected = nodeSelected.getRow(1).setClasses("x-grid-row");
            Table nodeEl;
            if (parent != null && nodeSelected.waitToRender(800L, false) && rowSelected.getAttributeClass().contains("x-grid-tree-node-expanded")) {
                nodeEl = new Table(nodeSelected).setClasses("x-grid-item").setTag("following::table").setChildNodes(textEl).setVisibility(true);
            } else {
                nodeEl = new Table(this).setClasses("x-grid-item").setChildNodes(textEl).setVisibility(true);
            }
            if (doScroll) {
                scrollPageDownTo(nodeEl);
            }
            Row row = nodeEl.getRow(1).setClasses("x-grid-row");
            WebLocator expanderEl = new WebLocator(nodeEl).setClasses("x-tree-expander");
            if (nodeEl.ready()) {
                String aClass = row.getAttributeClass();
                if (!(aClass.contains("x-grid-tree-node-expanded") || aClass.contains("x-grid-tree-node-leaf"))) {
                    expanderEl.click();
                } else {
                    WebLocator checkTree = new WebLocator(nodeEl).setClasses("x-tree-checkbox");
                    WebLocator nodeTree = new WebLocator(nodeEl).setClasses("x-tree-node-text");
                    try {
                        selected = checkTree.isElementPresent() ? checkTree.click() : nodeTree.click();
                    } catch (WebDriverException e) {
                        if (doScroll) {
                            scrollPageDown();
                        }
                        selected = checkTree.isElementPresent() ? checkTree.click() : nodeTree.click();
                    }
                }
            }
            parent = node;
        }
        return selected;
    }
}