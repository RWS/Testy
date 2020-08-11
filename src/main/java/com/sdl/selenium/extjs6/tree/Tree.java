package com.sdl.selenium.extjs6.tree;

import com.sdl.selenium.extjs6.grid.Cell;
import com.sdl.selenium.extjs6.grid.Scrollable;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.Row;
import com.sdl.selenium.web.table.Table;
import org.openqa.selenium.WebDriverException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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
            if (parent != null && nodeSelected.waitToRender(Duration.ofMillis(800), false) && rowSelected.getAttributeClass().contains("x-grid-tree-node-expanded")) {
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
                        selected = checkTree.isPresent() ? checkTree.click() : nodeTree.click();
                    } catch (WebDriverException e) {
                        if (doScroll) {
                            scrollPageDown();
                        }
                        selected = checkTree.isPresent() ? checkTree.click() : nodeTree.click();
                    }
                }
            }
            parent = node;
        }
        return selected;
    }

    public List<List<String>> getCellsText(int... excludedColumns) {
        return getCellsText(false, (short) 0, excludedColumns);
    }

    private List<List<String>> getCellsText(boolean rowExpand, short columnLanguages, int... excludedColumns) {
        com.sdl.selenium.extjs6.grid.Row rowsEl = new com.sdl.selenium.extjs6.grid.Row(this).setTag("tr");
        com.sdl.selenium.extjs6.grid.Row rowEl = new com.sdl.selenium.extjs6.grid.Row(this, 1);
        if (rowExpand) {
            rowsEl.setExcludeClasses("x-grid-rowbody-tr");
            rowEl = new com.sdl.selenium.extjs6.grid.Row(this).setTag("tr").setExcludeClasses("x-grid-rowbody-tr").setResultIdx(1);
        }
        Cell columnsEl = new Cell(rowEl);
        int rows = rowsEl.size();
        int columns = columnsEl.size();
        final List<Integer> columnsList = getColumns(columns, excludedColumns);
        if (rows <= 0) {
            return null;
        } else {
            return getLists(rows, rowExpand, columnLanguages, columnsList);
        }
    }

    private List<Integer> getColumns(int columns, int[] excludedColumns) {
        List<Integer> excluded = new ArrayList<>();
        for (int excludedColumn : excludedColumns) {
            excluded.add(excludedColumn);
        }

        List<Integer> columnsList = new ArrayList<>();
        for (int i = 1; i <= columns; i++) {
            if (!excluded.contains(i)) {
                columnsList.add(i);
            }
        }
        return columnsList;
    }

    private List<List<String>> getLists(int rows, boolean rowExpand, short columnLanguages, List<Integer> columnsList) {
       Row rowsEl = new Row(this);
        if (!rowExpand) {
            rowsEl.setTag("tr");
        }
        int size = rowsEl.size();
        List<List<String>> listOfList = new ArrayList<>();
        boolean canRead = true;
        String id = "";
        int timeout = 0;
        do {
            for (int i = 1; i <= rows; ++i) {
                if (canRead) {
                    List<String> list = new ArrayList<>();
                    for (int j : columnsList) {
                        Row row = new Row(this).setTag("tr").setResultIdx(i);
                        if (rowExpand) {
                            row.setExcludeClasses("x-grid-rowbody-tr");
                        }
                        Cell cell = new Cell(row, j);
                        String text;
                        if (columnLanguages == j) {
                            text = cell.getLanguages();
                        } else {
                            text = cell.getText(true).trim();
                        }
                        list.add(text);
                    }
                    listOfList.add(list);
                } else {
                    if (size == i + 1) {
                        break;
                    }
                    Row row = new Row(this).setResultIdx(i);
                    String currentId = row.getAttributeId();
                    if (!"".equals(id) && id.equals(currentId)) {
                        canRead = true;
                    }
                }
            }
            if (isScrollBottom()) {
                break;
            }
            Row row = new Row(this).setResultIdx(size);
            id = row.getAttributeId();
            scrollPageDownInTree();
            canRead = false;
            timeout++;
        } while (timeout < 30);
        return listOfList;
    }
}