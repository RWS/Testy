package com.sdl.selenium.extjs6.tree;

import com.google.common.base.Strings;
import com.sdl.selenium.Go;
import com.sdl.selenium.extjs6.grid.*;
import com.sdl.selenium.web.Editor;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.Transform;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.IHeaders;
import com.sdl.selenium.web.table.Table;
import com.sdl.selenium.web.utils.Response;
import com.sdl.selenium.web.utils.RetryUtils;
import com.sdl.selenium.web.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriverException;

import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class Tree extends WebLocator implements Scrollable, Editor, Transform, IHeaders {
    private static final Logger log = LogManager.getLogger(Tree.class);

    public Tree() {
        setClassName("Tree");
        setBaseCls("x-tree-panel");
        WebLocator header = new WebLocator().setClasses("x-title-text");
        setTemplateTitle(header);
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
        return doSelected(doScroll, nodes, action, searchTypes).isDone();
    }

    public Response<Row> doSelected(boolean doScroll, List<String> nodes, ConditionFunction<String, String, List<WebLocator>> function, Action action, SearchType... searchTypes) {
        Row previousNodeEl = null;
        boolean selected = false;
        for (int i = 0; i < nodes.size(); i++) {
            String node = nodes.get(i);
            List<WebLocator> children = new ArrayList<>();
            if (function.getCondition().test(node)) {
                children = function.getFunction().apply(node);
            } else {
                WebLocator textEl = new WebLocator().setText(node, searchTypes);
                children.add(textEl);
            }
            WebLocator container = previousNodeEl == null ? this : previousNodeEl;
            Row nodeEl = new Row(container).setClasses("x-grid-item").setChildNodes(children).setVisibility(true);
            if (previousNodeEl != null) {
                nodeEl.setRoot("/following-sibling::");
            }
            Row row = new Row(nodeEl, 1).setTag("tr").setClasses("x-grid-row");
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
                        boolean expanded = RetryUtils.retry(Duration.ofSeconds(2), () -> {
                            String aCls = row.getAttributeClass();
                            log.debug("classes:{}", aCls);
                            return aCls.contains("x-grid-tree-node-expanded");
                        });
                        if (expanded) {
                            log.info("Node '{}' is expanded.", node);
                        } else {
                            log.error("Node '{}' is not expanded!!!", node);
                        }
                        return expanded;
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
//            WebLocator containerOfParent = nodeEl.getPathBuilder().getContainer().getPathBuilder().getContainer();
//            if (i > 0) {
//                nodeEl.setContainer(containerOfParent);
//                nodeEl.setRoot("//");
//            }
            previousNodeEl = nodeEl;
        }
        return new Response<>(previousNodeEl, selected);
    }

    public Response<Row> doSelected(boolean doScroll, List<String> nodes, Action action, SearchType... searchTypes) {
        Row previousNodeEl = null;
        boolean selected = false;
        for (int i = 0; i < nodes.size(); i++) {
            String node = nodes.get(i);
            WebLocator textEl = new WebLocator().setText(node, searchTypes);
            WebLocator container = previousNodeEl == null ? this : previousNodeEl;
            Row nodeEl = new Row(container).setClasses("x-grid-item").setChildNodes(textEl).setVisibility(true);
            if (previousNodeEl != null) {
                nodeEl.setRoot("/following-sibling::");
            }
            Row row = new Row(nodeEl, 1).setTag("tr").setClasses("x-grid-row");
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
                        boolean expanded = RetryUtils.retry(Duration.ofSeconds(2), () -> {
                            String aCls = row.getAttributeClass();
                            log.debug("classes:{}", aCls);
                            return aCls.contains("x-grid-tree-node-expanded");
                        });
                        if (expanded) {
                            log.info("Node '{}' is expanded.", node);
                        } else {
                            log.error("Node '{}' is not expanded!!!", node);
                        }
                        return expanded;
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
//            WebLocator containerOfParent = nodeEl.getPathBuilder().getContainer().getPathBuilder().getContainer();
//            if (i > 0) {
//                nodeEl.setContainer(containerOfParent);
//                nodeEl.setRoot("//");
//            }
            previousNodeEl = nodeEl;
        }
        return new Response<>(previousNodeEl, selected);
    }

    public Row selectAndGetNode(boolean doScroll, List<String> nodes, SearchType... searchTypes) {
        return selectAndGetNode(doScroll, nodes, Action.CLICK, searchTypes);
    }

    public Row selectAndGetNode(boolean doScroll, List<String> nodes, Action action, SearchType... searchTypes) {
        if (doScroll) {
            scrollTop();
        }
        Response<Row> response = doSelected(doScroll, nodes, action, searchTypes);
        boolean selected = response.isDone();
        if (selected) {
            return response.getResult();
        } else {
            return null;
        }
    }

    public Row selectAndGetNode(boolean doScroll, List<String> nodes, ConditionFunction<String, String, List<WebLocator>> function, Action action, SearchType... searchTypes) {
        if (doScroll) {
            scrollTop();
        }
        Response<Row> response = doSelected(doScroll, nodes, function, action, searchTypes);
        boolean selected = response.isDone();
        if (selected) {
            return response.getResult();
        } else {
            return null;
        }
    }

    public boolean isSelected(String node) {
        WebLocator nodeEl = new WebLocator().setText(node);
        Table nodeSelected = new Table(this).setClasses("x-grid-item", "x-grid-item-selected").setChildNodes(nodeEl).setVisibility(true);
        return nodeSelected.isPresent();
    }

    public boolean isSelected(List<String> nodes, SearchType... searchTypes) {
        Table previousNodeEl = null;
        Table nodeEl = null;
        int count = 0;
        for (String node : nodes) {
            WebLocator textEl = new WebLocator().setText(node, searchTypes);
            WebLocator container = previousNodeEl == null ? this : previousNodeEl;
            nodeEl = new Table(container).setClasses("x-grid-item").setChildNodes(textEl).setVisibility(true);
            if (previousNodeEl != null) {
                nodeEl.setRoot("/following-sibling::");
            }
            WebLocator containerOfParent = nodeEl.getPathBuilder().getContainer().getPathBuilder().getContainer();
            if (containerOfParent != null) {
                nodeEl.setContainer(containerOfParent);
                nodeEl.setRoot("//");
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
            Row row1 = new Row(this).setTag("tr").setClasses("x-grid-tree-node-expanded").setExcludeClasses("x-grid-tree-node-leaf").setResultIdx(1);
            WebLocator expanderEl = new WebLocator(row).setClasses("x-tree-expander").setRender(Duration.ofSeconds(1));
            expanderEl.doClick();
            row1.ready();
            waitToActivate();
            size = rowsEl.size();
            if (size == 0) {
                scrollPageDown();
                waitToActivate();
                size = rowsEl.size();
                if (size == 0) {
                    scrollPageDown();
                    waitToActivate();
                    size = rowsEl.size();
                    if (size == 0) {
                        scrollPageDown();
                        waitToActivate();
                        size = rowsEl.size();
                        if (size == 0) {
                            if (isScrollBottom()) {
                                break;
                            }
                        }
                    }
                }
            }
        } while (size != 0);
    }

    public Response<Row> expandNode(List<String> nodes) {
        Row previousNodeEl = null;
        boolean expand = false;
        for (int i = 0; i < nodes.size(); i++) {
            String node = nodes.get(i);
            List<WebLocator> children = new ArrayList<>();
            WebLocator textEl = new WebLocator().setText(node);
            children.add(textEl);
            WebLocator container = previousNodeEl == null ? this : previousNodeEl;
            Row nodeEl = new Row(container).setClasses("x-grid-item").setChildNodes(children).setVisibility(true);
            if (previousNodeEl != null) {
                nodeEl.setRoot("/following-sibling::");
            }
            Row row = new Row(nodeEl, 1).setTag("tr").setClasses("x-grid-row");
            String aClass = row.getAttributeClass();
            boolean isExpanded = aClass != null && aClass.contains("x-grid-tree-node-expanded");
//                if (doScroll) {
//                    scrollPageDownTo(nodeEl);
//                }
            WebLocator expanderEl = new WebLocator(nodeEl).setClasses("x-tree-expander");
            if (nodeEl.ready()) {
                if (!(isExpanded || (aClass != null && aClass.contains("x-grid-tree-node-leaf"))) && expanderEl.isPresent()) {
                    expand = RetryUtils.retry(2, () -> {
                        expanderEl.doClick();
                        boolean expanded = RetryUtils.retry(Duration.ofSeconds(2), () -> {
                            String aCls = row.getAttributeClass();
                            log.debug("classes:{}", aCls);
                            return aCls.contains("x-grid-tree-node-expanded");
                        });
                        if (expanded) {
                            log.info("Node '{}' is expanded.", node);
                        } else {
                            log.error("Node '{}' is not expanded!!!", node);
                        }
                        return expanded;
                    });
                } else {
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
                }
            }
            previousNodeEl = nodeEl;
        }
        return new Response<>(previousNodeEl, expand);
    }

    public void collapseAllNodes() {
        scrollTop();
        Row rowsEl = new Row(this).setTag("tr").setClasses("x-grid-tree-node-expanded").setExcludeClasses("x-grid-tree-node-leaf");
        int size = rowsEl.size();
        for (int i = 1; i <= size; i++) {
            rowsEl.setResultIdx(i);
            WebLocator expanderEl = new WebLocator(rowsEl).setClasses("x-tree-expander");
            RetryUtils.retry(10, () -> {
                if (rowsEl.isPresent()) {
                    expanderEl.doClick();
                }
                return !rowsEl.ready(Duration.ofSeconds(1));
            });
        }
    }

    public List<List<String>> getValues(int... excludedColumns) {
        Row rowEl = new Row(this, 1);
        Cell columnsEl = new Cell(rowEl);
        int columns = columnsEl.size();
        Row rowsEl = new Row(this).setTag("tr");
        int rows = rowsEl.size();
        final List<Integer> columnsList = getColumns(columns, excludedColumns);
        return getValues(rows, columnsList, new Options<>(List.of()));
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
        return getNodeRow(nodes, searchTypes);
    }

    public Row getNodeRow(List<String> nodes, SearchType... searchTypes) {
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

    public static Function<Cell, String> getValue() {
        return cell -> {
            WebLocator el = new WebLocator(cell).setClasses("x-tree-elbow-img");
            int spaces = el.size() == 0 ? 0 : el.size() - 1;
            String text = cell.getText(true).trim();
            return ">".repeat(spaces) + text;
        };
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

    public List<List<String>> getNodesValues(List<String> nodes, Options<String> options, int... excludedColumns) {
        Row rowNode = getNode(nodes);
        List<List<String>> listOfList = new LinkedList<>();
        for (String node : nodes) {
            Row nodeRow = this.getRow(new Cell(1, node)).setResultIdx(1);
            List<String> cellsText = nodeRow.getCellsText(options, excludedColumns);
            listOfList.add(cellsText);
        }
        List<List<String>> values = getNodesValues(rowNode, options, excludedColumns);
        listOfList.addAll(values);
        return listOfList;
    }

    public List<List<String>> getCellsText(int... excludedColumns) {
        return getCellsText(new Options<>(List.of()), excludedColumns);
    }

    public <V> List<V> getCellsValues(V type, int... excludedColumns) {
        List<List<String>> cellsText = getCellsText(new Options<>(type), excludedColumns);
        List<Integer> columnsList = Arrays.stream(excludedColumns).boxed().toList();
        List<V> actualValues = transformTo(type, cellsText, columnsList);
        return actualValues;
    }

    public <V> List<V> getCellsValues(V type, boolean rowExpand, Predicate<Integer> predicate, Function<Cell, String> function, int... excludedColumns) {
        Options<V> options = new Options<>(type, rowExpand, predicate, function);
        List<List<String>> cellsText = getCellsText(options, excludedColumns);
        List<Integer> columnsList = Arrays.stream(excludedColumns).boxed().toList();
        List<V> actualValues = transformTo(type, cellsText, columnsList);
        return actualValues;
    }

    public <V> List<V> getCellsValues(Options<V> options, int... excludedColumns) {
        List<List<String>> cellsText = getCellsText(options, excludedColumns);
        List<Integer> columnsList = Arrays.stream(excludedColumns).boxed().toList();
        List<V> actualValues = transformTo(options.getType(), cellsText, columnsList);
        return actualValues;
    }

    public List<List<String>> getCellsText(Predicate<Integer> predicate, Function<Cell, String> function, int... excludedColumns) {
        Options<List<String>> options = new Options<>(List.of(), false, predicate, function);
        return getCellsText(options, excludedColumns);
    }

    /**
     * Retrieves the text content of the cells in the tree, using the provided options for custom extraction and excluding specified columns.
     * <p>
     * The {@code Options} parameter allows customization of how cell values are extracted, such as specifying a custom function or predicate for certain columns.
     * The {@code excludedColumns} parameter allows you to skip specific columns by their indices.
     *
     * @param options         an {@link Options} object that allows customization of how cell values are extracted (e.g., custom functions, predicates, type).
     * @param excludedColumns variable number of column indices to exclude from the result
     * @param <V>             the type parameter for the options, typically the type of the extracted value
     * @return a list of lists, where each inner list contains the text values of a row's cells, excluding the specified columns
     *
     * <b>Example usage:</b>
     * <pre>{@code
     * Options<List<String>> options = new Options<>(List.of());
     * List<List<String>> cellTexts = tree.getCellsText(options, 0, 2); // Excludes columns 0 and 2
     * }</pre>
     */
    public <V> List<List<String>> getCellsText(Options<V> options, int... excludedColumns) {
        final List<Integer> columnsList = getColumns(excludedColumns);
        List<List<String>> listOfList = new LinkedList<>();
        String id = "";
        String name = "";
        int i = 0;
        Row row = null;
        do {
            if (i % options.getResetIndex() == 0) {
                if (i != 0) {
                    id = row.getAttribute("id", true);
                }
                row = new Row(this);
            }
            if (i != 0) {
                if (!id.isEmpty()) {
                    row.setId(id);
                    id = "";
                }
                row.setChildNodes(new Cell(1, name));
                row = row.getNextRow();
            }
            if (i != 0 && i % 15 == 0) {
                row.scrollIntoView(Go.START);
            }
            i++;
            List<String> list = new LinkedList<>();
            for (int j : columnsList) {
                Cell cell = new Cell(row, j);
                Optional<Predicate<Integer>> first = options.getFunctions().keySet().stream().filter((p) -> p.test(j)).findFirst();
                String text;
                if (first.isPresent()) {
                    Predicate<Integer> predicate = first.get();
                    Function<Cell, String> function = options.getFunctions().get(predicate);
                    text = function.apply(cell);
                } else {
                    text = cell.getText(true);
                }
                try {
                    text = text.trim();
                } catch (NullPointerException e) {
                    Utils.sleep(1);
                }
                list.add(text);
            }
            name = list.get(0);
            List<String> listTMP = options.isAlignment() ? alignment(row, list) : list;
            listOfList.add(listTMP);
        } while (row.getNextRow().isPresent());
        return listOfList;
    }

    public <V> List<List<String>> getCellsTextV0(Options<V> options, int... excludedColumns) {
        Row rowsEl = new Row(this).setTag("tr");
        if (options.isExpand()) {
            rowsEl.setExcludeClasses("x-grid-rowbody-tr");
        }
        int rows = rowsEl.size();
        final List<Integer> columnsList = getColumns(excludedColumns);
        if (rows <= 0) {
            return null;
        } else {
            return getLists(rows, options, columnsList);
        }
    }

    @Override
    public int getHeadersCount() {
        Row row = new Row(this, 1);
        return row.getCells();
    }


    public <V> List<List<String>> getValues(int rows, List<Integer> columnsList, Options<V> options) {
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
                        Optional<Predicate<Integer>> first = options.getFunctions().keySet().stream().filter(p -> p.test(j)).findFirst();
                        if (first.isPresent()) {
                            Predicate<Integer> predicate = first.get();
                            Function<Cell, String> function = options.getFunctions().get(predicate);
                            text = function.apply(cell);
                        } else {
                            try {
                                text = cell.getText(true).trim();
                            } catch (Exception e) {
                                text = "";
                            }
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

    private <V> List<List<String>> getNodesValues(Row rowNode, Options<V> options, int... excludedColumns) {
        List<List<String>> listOfList = new LinkedList<>();
        Row nextRow = rowNode.getNextRow();
        while (nextRow.ready()) {
            Row row = nextRow.getNextRow();
            Row rowTMP = row.clone(row);
            rowTMP.setTag("tr").setClasses("x-grid-tree-node-leaf");
            if (!rowTMP.isPresent()) {
                break;
            }
            List<String> actualValues = rowTMP.getCellsText((Options<String>) options, excludedColumns);
            listOfList.add(actualValues);
            nextRow = row;
        }
        return listOfList;
    }

    public <V> List<List<String>> getLists(int rows, Options<V> options, List<Integer> columnsList) {
        Row rowsEl = new Row(this);
        if (!options.isExpand()) {
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
                    List<List<String>> list = options.getCollector() == null ? collector(options, columnsList, i) : options.getCollector().apply(new Details<>(options, columnsList, this, i));
                    listOfList.addAll(list);
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

    private <V> List<List<String>> collector(Options<V> options, List<Integer> columnsList, int i) {
        List<List<String>> listOfList = new LinkedList<>();
        List<String> list = new LinkedList<>();
        for (int j : columnsList) {
            Row row = new Row(this).setTag("tr").setResultIdx(i);
            if (options.isExpand()) {
                row.setExcludeClasses("x-grid-rowbody-tr");
            }
            Cell cell = new Cell(row, j);
            String text;
            Optional<Predicate<Integer>> first = options.getFunctions().keySet().stream().filter(p -> p.test(j)).findFirst();
            if (first.isPresent()) {
                Predicate<Integer> predicate = first.get();
                Function<Cell, String> function = options.getFunctions().get(predicate);
                text = function.apply(cell);
            } else {
                text = cell.getText(true).trim();
                if (Strings.isNullOrEmpty(text)) {
                    text = cell.getText(true).trim();
                }
            }
            list.add(text);
        }
        listOfList.add(list);
        return listOfList;
    }

    public Row getRow(String searchElement, SearchType... searchTypes) {
        return new Row(this, searchElement, searchTypes).setInfoMessage("-Row");
    }

    public Row getRow(Cell... byCells) {
        return new Row(this, byCells).setInfoMessage("-Row");
    }

    public boolean waitToActivate(Duration duration) {
        boolean hasMask;
        long startMs = System.currentTimeMillis();
        long timeMs = 0L;
        while ((hasMask = hasMask()) && (timeMs < duration.toMillis())) {
            timeMs = System.currentTimeMillis() - startMs;
        }
        long endMs = System.currentTimeMillis();
        log.info("waitToActivate:{} milliseconds; {}", endMs - startMs, toString());
        return !hasMask;
    }

    private boolean hasMask() {
        WebLocator mask = new WebLocator(this).setClasses("x-mask").setElPathSuffix("style", "not(contains(@style, 'display: none'))").setAttribute("aria-hidden", "false").setInfoMessage("Mask");
        return mask.waitToRender(Duration.ofMillis(200L), false);
    }
}

