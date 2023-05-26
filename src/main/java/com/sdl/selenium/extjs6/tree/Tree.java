package com.sdl.selenium.extjs6.tree;

import com.sdl.selenium.extjs6.grid.Cell;
import com.sdl.selenium.extjs6.grid.Row;
import com.sdl.selenium.extjs6.grid.Scrollable;
import com.sdl.selenium.web.Editor;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.Transform;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.Table;
import com.sdl.selenium.web.utils.RetryUtils;
import com.sdl.selenium.web.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriverException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Tree extends WebLocator implements Scrollable, Editor, Transform {
    private static final Logger log = LogManager.getLogger(Tree.class);

    public Tree() {
        setClassName("Tree");
        setBaseCls("x-tree-panel");
    }

    public Tree(WebLocator container) {
        this();
        setContainer(container);
    }

    @Override
    public WebLocator getView() {
        return this;
    }

    @Deprecated
    public boolean select(String... nodes) {
        return select(false, nodes);
    }

    public boolean select(List<String> nodes) {
        return select(false, nodes, Action.CLICK);
    }

    @Deprecated
    public boolean select(boolean doScroll, String... nodes) {
        return select(doScroll, List.of(nodes), Action.CLICK, SearchType.EQUALS, SearchType.TRIM);
    }

    public boolean select(List<String> nodes, SearchType... searchTypes) {
        return select(false, nodes, Action.CLICK, searchTypes);
    }

    public boolean select(List<String> nodes, Action action, SearchType... searchTypes) {
        return select(false, nodes, action, searchTypes);
    }

    public boolean select(boolean doScroll, List<String> nodes, SearchType... searchTypes) {
        return select(doScroll, nodes, Action.CLICK, searchTypes);
    }

    public boolean select(boolean doScroll, List<String> nodes, Action action, SearchType... searchTypes) {
        if (doScroll) {
            scrollTop();
        }
        Table previousNodeEl = null;
        boolean selected = false;
        for (int i = 0; i < nodes.size(); i++) {
            String node = nodes.get(i);
            WebLocator textEl = new WebLocator().setText(node, searchTypes);
            WebLocator container = previousNodeEl == null ? this : previousNodeEl;
            Table nodeEl = new Table(container).setClasses("x-grid-item").setChildNodes(textEl).setVisibility(true);
            if (previousNodeEl != null) {
                nodeEl.setRoot("/following-sibling::");
            }
            com.sdl.selenium.web.table.Row row = nodeEl.getRow(1).setClasses("x-grid-row");
            boolean isExpanded;
            String aClass = row.getAttributeClass();
            isExpanded = aClass != null && aClass.contains("x-grid-tree-node-expanded");
            if (doScroll) {
                scrollPageDownTo(nodeEl);
            }
            WebLocator expanderEl = new WebLocator(nodeEl).setClasses("x-tree-expander");
            if (nodeEl.ready()) {
                if (!(isExpanded || (aClass != null && aClass.contains("x-grid-tree-node-leaf"))) && expanderEl.isPresent()) {
                    RetryUtils.retry(2, () -> {
                        expanderEl.click();
                        String aCls = row.getAttributeClass();
                        boolean contains = aCls.contains("x-grid-tree-node-expanded");
                        if (!contains) {
                            Utils.sleep(1);
                            log.error("Node '{}' is not expanded!!!", node);
                        } else {
                            log.info("Node '{}' is expanded.", node);
                        }
                        return contains;
                    });
                } else {
                    WebLocator checkTree = new WebLocator(nodeEl).setClasses("x-tree-checkbox");
                    WebLocator nodeTree = new WebLocator(nodeEl).setClasses("x-tree-node-text");
                    int nodeCount = nodeTree.size();
                    if (nodeCount > 1) {
                        WebLocator precedingSibling = new WebLocator(nodeTree).setTag("preceding-sibling::*").setClasses("x-tree-elbow-img");
                        for (int j = 1; j <= nodeCount; j++) {
                            nodeTree.setResultIdx(j);
                            int size = precedingSibling.size();
                            if (size == i + 1) {
                                break;
                            }
                        }
                    }
                    try {
                        if (checkTree.isPresent()) {
                            selected = checkTree.click();
                        } else {
                            selected = RetryUtils.retry(2, () -> action.name().equals("CLICK") ? nodeTree.click() : nodeTree.mouseOver());
                        }
                    } catch (WebDriverException e) {
                        if (doScroll) {
                            scrollPageDown();
                        }
                        if (checkTree.isPresent()) {
                            selected = checkTree.click();
                        } else {
                            selected = RetryUtils.retry(2, () -> action.name().equals("CLICK") ? nodeTree.click() : nodeTree.mouseOver());
                        }
                    }
                }
            }
            previousNodeEl = nodeEl;
        }
        return selected;
    }

    public boolean isSelected(String node) {
        WebLocator nodeEl = new WebLocator().setText(node);
        Table nodeSelected = new Table(this).setClasses("x-grid-item", "x-grid-item-selected").setChildNodes(nodeEl).setVisibility(true);
        return nodeSelected.isPresent();
    }

    public boolean isSelected(List<String> nodes) {
        Table previousNodeEl = null;
        Table nodeEl = null;
        int count = 0;
        for (String node : nodes) {
            WebLocator textEl = new WebLocator().setText(node, SearchType.EQUALS);
            WebLocator container = previousNodeEl == null ? this : previousNodeEl;
            nodeEl = new Table(container).setClasses("x-grid-item").setChildNodes(textEl).setVisibility(true);
            if (previousNodeEl != null) {
                nodeEl.setRoot("/following-sibling::");
            }
            previousNodeEl = nodeEl;
            count++;
        }
        int nodeCount = nodeEl.size();
        if (nodeCount > 1) {
            WebLocator nodeTree = new WebLocator(nodeEl).setClasses("x-tree-node-text");
            WebLocator precedingSibling = new WebLocator(nodeTree).setTag("preceding-sibling::*").setClasses("x-tree-elbow-img");
            for (int j = 1; j <= nodeCount; j++) {
                nodeTree.setResultIdx(j);
                int size = precedingSibling.size();
                if (size == count) {
                    nodeEl.setResultIdx(j);
                    break;
                }
            }
        }
        String aClass = nodeEl.getAttributeClass();
        return aClass.contains("x-grid-item-selected");
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
        return getValues(rows, columnsList, t -> t == 0, null);
    }

    public Row getNode(List<String> nodes, SearchType... searchTypes) {
        return getNode(false, nodes, searchTypes);
    }

    public Row getNode(List<String> nodes, Action action, SearchType... searchTypes) {
        return getNode(false, nodes, action, searchTypes);
    }

    public Row getNode(boolean doScroll, List<String> nodes, SearchType... searchTypes) {
        return getNode(doScroll, nodes, Action.CLICK, searchTypes);
    }

    public Row getNode(boolean doScroll, List<String> nodes, Action action, SearchType... searchTypes) {
        select(doScroll, nodes, action);
        int size = nodes.size();
        if (size == 0) {
            return null;
        } else if (size == 1) {
            return getRow(new Cell(1, nodes.get(0), searchTypes));
        } else {
            Row row = new Row(this, new Cell(1, nodes.get(size - 2), searchTypes)) {
                public Row getNextRow() {
                    return new Row(this).setRoot("/").setTag("following-sibling::table");
                }
            };
            Row nextRow = row.getNextRow();
            Cell cell = new Cell(1, nodes.get(size - 1), searchTypes);
            nextRow.setChildNodes(cell);
            return nextRow;
        }
    }

    public List<List<String>> getNodesValues(List<String> nodes, int... excludedColumns) {
        return getNodesValues(nodes, t -> t == 0, null, excludedColumns);
    }

    public List<List<String>> getNodesValues(List<String> nodes, Predicate<Integer> predicate, Function<Cell, String> function, int... excludedColumns) {
        Row rowNode = getNode(nodes);
        List<List<String>> listOfList = new LinkedList<>();
        for (String node : nodes) {
            Row nodeRow = this.getRow(new Cell(1, node)).setResultIdx(1);
            List<String> cellsText = nodeRow.getCellsText(predicate, function, excludedColumns);
            listOfList.add(cellsText);
        }
        List<List<String>> values = getNodesValues(rowNode, predicate, function, excludedColumns);
        listOfList.addAll(values);
        return listOfList;
    }

    public List<List<String>> getCellsText(int... excludedColumns) {
        return getCellsText(false, t -> t == 0, Cell::getLanguages, excludedColumns);
    }

    /**
     * add in V class this: @JsonInclude(JsonInclude.Include.NON_NULL)
     */
    public <V> List<V> getCellsValues(V type, int... excludedColumns) {
        List<List<String>> cellsText = getCellsText(false, t -> t == 0, Cell::getLanguages, excludedColumns);
        List<V> actualValues = transformToObjectList(type, cellsText);
        return actualValues;
    }

    /**
     * add in V class this: @JsonInclude(JsonInclude.Include.NON_NULL)
     */
    public <V> List<V> getCellsValues(V type, boolean rowExpand, Predicate<Integer> predicate, Function<Cell, String> function, int... excludedColumns) {
        List<List<String>> cellsText = getCellsText(rowExpand, predicate, function, excludedColumns);
        List<V> actualValues = transformToObjectList(type, cellsText);
        return actualValues;
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

    public List<List<String>> getValues(int rows, List<Integer> columnsList, Predicate<Integer> predicate, Function<Cell, String> function) {
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
        } while (timeout < 60);
        return listOfList;
    }

    private List<List<String>> getNodesValues(Row rowNode, Predicate<Integer> predicate, Function<Cell, String> function, int... excludedColumns) {
        List<List<String>> listOfList = new LinkedList<>();
        Row nextRow = rowNode.getNextRow();
        while (nextRow.ready()) {
            Row row = nextRow.getNextRow();
            Row rowTMP = row.clone(row);
            rowTMP.setTag("tr").setClasses("x-grid-tree-node-leaf");
            if (!rowTMP.isPresent()) {
                break;
            }
            List<String> actualValues = rowTMP.getCellsText(predicate, function, excludedColumns);
            listOfList.add(actualValues);
            nextRow = row;
        }
        return listOfList;
    }

    public List<List<String>> getLists(int rows, boolean rowExpand, Predicate<Integer> predicate, Function<Cell, String> function, List<Integer> columnsList) {
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
}