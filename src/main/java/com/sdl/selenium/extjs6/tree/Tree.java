package com.sdl.selenium.extjs6.tree;

import com.sdl.selenium.extjs6.grid.Cell;
import com.sdl.selenium.extjs6.grid.Scrollable;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.AbstractCell;
import com.sdl.selenium.web.table.Table;
import com.sdl.selenium.web.utils.Utils;
import org.openqa.selenium.WebDriverException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

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
            WebLocator textEl = new WebLocator().setText(node, SearchType.EQUALS);
            Table nodeSelected = new Table(this).setClasses("x-grid-item", "x-grid-item-selected");
            com.sdl.selenium.web.table.Row rowSelected = nodeSelected.getRow(1).setClasses("x-grid-row");
            Table nodeEl;
            if (parent != null && nodeSelected.waitToRender(Duration.ofMillis(800), false) && rowSelected.getAttributeClass().contains("x-grid-tree-node-expanded")) {
                nodeEl = new Table(nodeSelected).setClasses("x-grid-item").setTag("following::table").setChildNodes(textEl).setVisibility(true);
            } else {
                nodeEl = new Table(this).setClasses("x-grid-item").setChildNodes(textEl).setVisibility(true);
            }
            if (doScroll) {
                scrollPageDownTo(nodeEl);
            }
            com.sdl.selenium.web.table.Row row = nodeEl.getRow(1).setClasses("x-grid-row");
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

    public boolean isSelected(String node){
        WebLocator nodeEl = new WebLocator().setText(node);
        Table nodeSelected = new Table(this).setClasses("x-grid-item", "x-grid-item-selected").setChildNodes(nodeEl).setVisibility(true);
        return nodeSelected.isPresent();
    }

    public void expandAllNodes() {
        Row rowsEl = new Row(this).setTag("tr").setExcludeClasses("x-grid-tree-node-leaf", "x-grid-tree-node-expanded");
        int size;
        do {
            Row row = new Row(this).setTag("tr").setExcludeClasses("x-grid-tree-node-leaf", "x-grid-tree-node-expanded").setResultIdx(1);
            WebLocator expanderEl = new WebLocator(row).setClasses("x-tree-expander").setRender(Duration.ofSeconds(1));
            expanderEl.doClick();
            size = rowsEl.size();
            if (size == 0) {
                scrollPageDown();
                size = rowsEl.size();
                if (size == 0) {
                    scrollPageDown();
                    size = rowsEl.size();
                    if (size == 0) {
                        Utils.sleep(1);
                    }
                }
            }
        } while (size != 0);
    }

    public List<List<String>> getValues(int... excludedColumns) {
        Row rowEl = new Row(this, 1);
        Cell columnsEl = new Cell(rowEl);
        int columns = columnsEl.size();
        Row rowsEl = new Row(this).setTag("tr");
        int rows = rowsEl.size();
        final List<Integer> columnsList = getColumns(columns, excludedColumns);
        return getValues(rows, columnsList);
    }

    public List<List<String>> getNodesValues(List<String> nodes, int... excludedColumns) {
        select(nodes.toArray(new String[0]));
        Row rowEl = new Row(this, 1);
        Cell columnsEl = new Cell(rowEl);
        int columns = columnsEl.size();
        List<List<String>> listOfList = new ArrayList<>();
        for (String node : nodes) {
            Row nodeRow = getRow(new Cell(1, node)).setResultIdx(1);
            List<String> cellsText = nodeRow.getCellsText(excludedColumns);
            listOfList.add(cellsText);
        }
        Row rowsEl = new Row(this).setTag("tr").setClasses("x-grid-tree-node-leaf");
        int rows = rowsEl.size();
        final List<Integer> columnsList = getColumns(columns, excludedColumns);
        List<List<String>> lists = getValues(rows, columnsList);
        listOfList.addAll(lists);
        return listOfList;
    }

    public List<List<String>> getCellsText(int... excludedColumns) {
        return getCellsText(false, t -> t == 0, Cell::getLanguages, excludedColumns);
    }

    public List<List<String>> getCellsText(boolean rowExpand, Predicate<Integer> predicate, Function<Cell, String> function, int... excludedColumns) {
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
            return getLists(rows, rowExpand, predicate, function, columnsList);
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

    private List<List<String>> getValues(int rows, List<Integer> columnsList) {
        Row rowsEl = new Row(this).setTag("tr");
        int size = rowsEl.size();
        List<List<String>> listOfList = new LinkedList<>();
        boolean canRead = true;
        String id = "";
        int timeout = 0;
        do {
            for (int i = 1; i <= rows; ++i) {
                if (canRead) {
                    List<String> list = new LinkedList<>();
                    for (int j : columnsList) {
                        Row row = new Row(this).setTag("tr").setResultIdx(i);
                        Cell cell = new Cell(row, j);
                        String text = cell.getText(true).trim();
                        list.add(text);
                    }
                    listOfList.add(list);
                } else {
                    if (size == i + 1) {
                        break;
                    }
                    Row row = new Row(this, i);
                    String currentId = row.getAttributeId();
                    if (!"".equals(id) && id.equals(currentId)) {
                        canRead = true;
                    }
                }
            }
            if (isScrollBottom()) {
                break;
            }
            Row row = new Row(this, size);
            id = row.getAttributeId();
            scrollPageDownInTree();
            canRead = false;
            timeout++;
        } while (timeout < 30);
        return listOfList;
    }

    private List<List<String>> getLists(int rows, boolean rowExpand, Predicate<Integer> predicate, Function<Cell, String> function, List<Integer> columnsList) {
        Row rowsEl = new Row(this);
        if (!rowExpand) {
            rowsEl.setTag("tr");
        }
        int size = rowsEl.size();
        List<List<String>> listOfList = new LinkedList<>();
        boolean canRead = true;
        String id = "";
        int timeout = 0;
        do {
            for (int i = 1; i <= rows; ++i) {
                if (canRead) {
                    List<String> list = new LinkedList<>();
                    for (int j : columnsList) {
                        Row row = new Row(this).setTag("tr").setResultIdx(i);
                        if (rowExpand) {
                            row.setExcludeClasses("x-grid-rowbody-tr");
                        }
                        Cell cell = new Cell(row, j);
                        String text;
                        if (predicate.test(j)) {
                            text = function.apply(cell);
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
                    Row row = new Row(this, i);
                    String currentId = row.getAttributeId();
                    if (!"".equals(id) && id.equals(currentId)) {
                        canRead = true;
                    }
                }
            }
            if (isScrollBottom()) {
                break;
            }
            Row row = new Row(this, size);
            id = row.getAttributeId();
            scrollPageDownInTree();
            canRead = false;
            timeout++;
        } while (timeout < 30);
        return listOfList;
    }

    public Row getRow(String searchElement, SearchType... searchTypes) {
        return new Row(this, searchElement, searchTypes).setInfoMessage("-Row");
    }

    public Row getRow(Cell... byCells) {
        return new Row(this, byCells).setInfoMessage("-Row");
    }

    static class Row extends com.sdl.selenium.extjs6.grid.Row {
        public Row() {
            super();
        }

        public Row(WebLocator grid) {
            super(grid);
        }

        public Row(WebLocator grid, int indexRow) {
            super(grid, indexRow);
        }

        public Row(WebLocator grid, String searchElement, SearchType... searchTypes) {
            super(grid, searchElement, searchTypes);
        }

        public Row(WebLocator grid, AbstractCell... cells) {
            super(grid, cells);
        }

        public Row(WebLocator grid, int indexRow, AbstractCell... cells) {
            super(grid, indexRow, cells);
        }
    }
}