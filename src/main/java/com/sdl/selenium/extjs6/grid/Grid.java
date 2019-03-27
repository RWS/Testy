package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.extjs6.form.ComboBox;
import com.sdl.selenium.extjs6.form.DateField;
import com.sdl.selenium.extjs6.form.TextArea;
import com.sdl.selenium.extjs6.form.TextField;
import com.sdl.selenium.utils.config.WebLocatorConfig;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.Table;
import com.sdl.selenium.web.utils.RetryUtils;
import com.sdl.selenium.web.utils.Utils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Slf4j
public class Grid extends Table implements Scrollable {
    private String version;

    public Grid() {
        setClassName("Grid");
        setBaseCls("x-grid");
        setTag("*");
        WebLocator header = new WebLocator().setClasses("x-title-text");
        setTemplateTitle(header);
    }

    public Grid(WebLocator container) {
        this();
        setContainer(container);
    }

    /**
     * <pre>{@code
     * Grid grid = new Grid().setHeaders("Company", "Price", "Change");
     * }</pre>
     *
     * @param headers grid's headers in any order
     * @param <T>     element which extended the Grid
     * @return this Grid
     */
    public <T extends Table> T setHeaders(final String... headers) {
        return setHeaders(false, headers);
    }

    /**
     * <pre>{@code
     * Grid grid = new Grid().setHeaders(true, "Company", "Price", "Change");
     * }</pre>
     *
     * @param strictPosition true if grid's headers is order
     * @param headers        grid's headers in order, if grid has no header put empty string
     * @param <T>            element which extended the Table
     * @return this Grid
     */
    public <T extends Table> T setHeaders(boolean strictPosition, final String... headers) {
        List<WebLocator> list = new ArrayList<>();
        for (int i = 0; i < headers.length; i++) {
            WebLocator headerEL = new WebLocator(this).setClasses("x-column-header").
                    setText(headers[i], SearchType.DEEP_CHILD_NODE_OR_SELF, SearchType.EQUALS);
            if (strictPosition) {
                headerEL.setTag("*[" + (i + 1) + "]");
            }
            list.add(headerEL);
        }
        setChildNodes(list.toArray(new WebLocator[0]));
        return (T) this;
    }

    @Override
    public Row getRow(int rowIndex) {
        return new Row(this, rowIndex).setInfoMessage("-Row");
    }

    public Group getGroup(String groupName) {
        return new Group(this, groupName).setInfoMessage("-Group");
    }

    public Group getGroup(int rowIndex) {
        return new Group(this, rowIndex).setInfoMessage("-Group");
    }

    @Override
    public Row getRow(String searchElement) {
        return new Row(this, searchElement, SearchType.EQUALS).setInfoMessage("-Row");
    }

    @Override
    public Row getRow(String searchElement, SearchType... searchTypes) {
        return new Row(this, searchElement, searchTypes).setInfoMessage("-Row");
    }

    public Row getRow(Cell... byCells) {
        return new Row(this, byCells).setInfoMessage("-Row");
    }

    public Row getRow(int indexRow, Cell... byCells) {
        return new Row(this, indexRow, byCells).setInfoMessage("-Row");
    }

    @Override
    public Cell getCell(int rowIndex, int columnIndex) {
        Row row = getRow(rowIndex);
        return new Cell(row, columnIndex).setInfoMessage("cell - Table");
    }

    @Override
    public Cell getCell(String searchElement, SearchType... searchTypes) {
        Row row = new Row(this);
        return new Cell(row).setText(searchElement, searchTypes);
    }

    public Cell getCell(int rowIndex, int columnIndex, String text) {
        Row row = getRow(rowIndex);
        return new Cell(row, columnIndex, text, SearchType.EQUALS);
    }

    public Cell getCell(String searchElement, String columnText, SearchType... searchTypes) {
        Row row = getRow(searchElement, SearchType.CONTAINS);
        return new Cell(row).setText(columnText, searchTypes);
    }

    @Override
    public Cell getCell(String searchElement, int columnIndex, SearchType... searchTypes) {
        return new Cell(new Row(this, searchElement, searchTypes), columnIndex);
    }

    public Cell getCell(int columnIndex, Cell... byCells) {
        return new Cell(getRow(byCells), columnIndex);
    }

    public Cell getCell(int columnIndex, String text, Cell... byCells) {
        return new Cell(getRow(byCells), columnIndex, text, SearchType.EQUALS);
    }

    protected String getVersion() {
        return version == null ? WebLocatorConfig.getExtJsVersion() : version;
    }

    public <T extends Grid> T setVersion(String version) {
        this.version = version;
        return (T) this;
    }

    public boolean waitToActivate(int seconds) {
        String info = toString();
        int count = 0;
        boolean hasMask;
        while ((hasMask = hasMask()) && (count < seconds)) {
            count++;
            log.info("waitToActivate:" + (seconds - count) + " seconds; " + info);
            Utils.sleep(900);
        }
        return !hasMask;
    }

    private boolean hasMask() {
        WebLocator mask = new WebLocator(this).setClasses("x-mask").setElPathSuffix("style", "not(contains(@style, 'display: none'))").setAttribute("aria-hidden", "false").setInfoMessage("Mask");
        return mask.waitToRender(500L, false);
    }

    @Override
    public boolean waitToPopulate(int seconds) {
        Row row = getRow(1).setVisibility(true).setRoot("//..//").setInfoMessage("first Row");
        WebLocator body = new WebLocator(this).setClasses("x-grid-header-ct"); // TODO see if must add for all rows
        row.setContainer(body);
        return row.waitToRender(seconds * 1000L);
    }

    public List<String> getHeaders() {
        WebLocator header = new WebLocator(this).setClasses("x-grid-header-ct");
        String headerText = RetryUtils.retrySafe(4, header::getText);
        return new ArrayList<>(Arrays.asList(headerText.trim().split("\n")));
    }

    private List<List<String>> getLists(int rows, boolean rowExpand, List<Integer> columnsList) {
        Row rowsEl = new Row(this);
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
                        String text = cell.getText(true).trim();
                        list.add(text);
                    }
                    listOfList.add(list);
                } else {
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

    @Override
    public List<List<String>> getCellsText(int... excludedColumns) {
        return getCellsText(false, false, excludedColumns);
    }

    public List<List<String>> getCellsText(boolean rowExpand, int... excludedColumns) {
        return getCellsText(false, rowExpand, excludedColumns);
    }

    public List<List<String>> getCellsText(boolean parallel, boolean rowExpand, int... excludedColumns) {
        Row rowsEl = new Row(this).setTag("tr");
        Row rowEl = new Row(this, 1);
        if (rowExpand) {
            rowsEl.setExcludeClasses("x-grid-rowbody-tr");
            rowEl = new Row(this).setTag("tr").setExcludeClasses("x-grid-rowbody-tr").setResultIdx(1);
        }
        Cell columnsEl = new Cell(rowEl);
        int rows = rowsEl.size();
        int columns = columnsEl.size();
        final List<Integer> columnsList = getColumns(columns, excludedColumns);
        if (rows <= 0) {
            return null;
        } else {
            return parallel ? getListsParallel(rows, rowExpand, columnsList) : getLists(rows, rowExpand, columnsList);
        }
    }

    private List<List<String>> getListsParallel(int rows, boolean rowExpand, List<Integer> columnsList) {
        List<List<String>> listOfList = new ArrayList<>();
        boolean canRead = true;
        String id = "";
        int timeout = 0;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        do {
            List<Future<List<String>>> futures = new ArrayList<>();
            for (int i = 1; i <= rows; ++i) {
                if (canRead) {
                    final int fi = i;
                    futures.add(executorService.submit(() -> {
                        List<String> list = new ArrayList<>();
                        for (int j : columnsList) {
                            Grid parent = (Grid) clone();
                            Row row = new Row(parent).setTag("tr").setResultIdx(fi);
                            if (rowExpand) {
                                row.setExcludeClasses("x-grid-rowbody-tr");
                            }
                            Cell cell = new Cell(row.clone(), j);
                            String text = "";
                            try {
                                text = cell.getText(true).trim();
                            } catch (NullPointerException e) {
                                Utils.sleep(1);
                            }
                            list.add(text);
                        }
                        return list;
                    }));
                } else {
                    Row row = new Row(this, i);
                    String currentId = row.getAttributeId();
                    if (!"".equals(id) && id.equals(currentId)) {
                        canRead = true;
                    }
                }
            }
            for (Future<List<String>> future : futures) {
                try {
                    listOfList.add(future.get());
                } catch (InterruptedException | ExecutionException e) {
                    log.debug("{}", e);
                }
            }
            if (isScrollBottom()) {
                break;
            }
            Row row = new Row(this, rows);
            id = row.getAttributeId();
            scrollPageDownInTree();
            canRead = false;
            timeout++;
        } while (timeout < 30);
        executorService.shutdown();
        return listOfList;
    }

    private String getTextNode(Cell cell) {
        String text = cell.getText(true).trim();
//        WebLocator childs = new WebLocator(cell).setClasses("user-avatar");
//        if (childs.waitToRender(150L, false)) {
//            List<WebElement> children = childs.findElements();
//            for (WebElement child : children) {
//                text = text.replaceFirst(child.getText(), "").trim();
//            }
//        }
        return text;
    }

    public List<List<String>> getCellsText(String group, int... excludedColumns) {
        Group groupEl = getGroup(group);
        groupEl.expand();
        List<Row> groupElRows = groupEl.getRows();
        Cell columnsEl = new Cell(groupElRows.get(1));
        int rows = groupElRows.size();
        int columns = columnsEl.size();
        List<Integer> columnsList = getColumns(columns, excludedColumns);

        if (rows <= 0) {
            return null;
        } else {
            List<List<String>> listOfList = new ArrayList<>();
            boolean canRead = true;
            String id = "";
            int timeout = 0;
            do {
                for (int i = 0; i < rows; ++i) {
                    if (canRead) {
                        List<String> list = new ArrayList<>();
                        for (int j : columnsList) {
                            String text = getTextNode(groupElRows.get(i).getCell(j));
                            list.add(text);
                        }
                        listOfList.add(list);
                    } else {
                        String currentId = new Row(this, i + 1).getAttributeId();
                        if (!"".equals(id) && id.equals(currentId)) {
                            canRead = true;
                        }
                    }
                }
                if (isScrollBottom() || listOfList.size() >= rows) {
                    break;
                }
                id = new Row(this, rows).getAttributeId();
                scrollPageDownInTree();
                canRead = false;
                timeout++;
            } while (listOfList.size() < rows && timeout < 30);
            return listOfList;
        }
    }


    public <V> List<V> getCellsText(Class<V> type, int... excludedColumns) {
        Class<?> newclazz = null;
        int s = 0;
        Class[] parameterTypes = null;
        try {
            newclazz = Class.forName(type.getTypeName());
            s = newclazz.getDeclaredFields().length;
            Constructor[] constructors = newclazz.getConstructors();
            for (Constructor c : constructors) {
                if (s == c.getParameterCount()) {
                    parameterTypes = c.getParameterTypes();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Class<?> finalNewclazz = newclazz;
        int finalS = s;
        Class[] finalParameterTypes = parameterTypes;
        return getCellsText(excludedColumns).stream().map(t -> {
            List<Object> arr = new ArrayList<>();
            try {
                for (int i = 0; i < finalS; i++) {
                    arr.add(t.get(i));
                }
                Constructor<V> constructor = (Constructor<V>) finalNewclazz.getConstructor(finalParameterTypes);
                return constructor.newInstance(arr.toArray(new Object[0]));
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

    @Override
    public int getCount() {
        if (ready(true)) {
            return new Row(this).size();
        } else {
            return -1;
        }
    }

    public List<String> getGroupsName() {
        Group group = new Group(this);
        int size = group.size();
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            group.setResultIdx(i);
            list.add(group.getNameGroup());
        }
        return list;
    }

    public String getNextGroupName(String groupName) {
        Group group = new Group(this);
        int size = group.size();
        for (int i = 1; i < size; i++) {
            group.setResultIdx(i);
            String g = group.getNameGroup().toLowerCase();
            if (g.contains(groupName.toLowerCase())) {
                group.setResultIdx(i + 1);
                return group.getNameGroup();
            }
        }
        return null;
    }

    public void selectAll() {
        WebLocator checkBox = new WebLocator(this).setBaseCls("x-column-header-checkbox");
        checkBox.click();
    }

    public <T extends TextField> T getEditor() {
        TextField editor;
        WebLocator container = new WebLocator("x-editor", this);
        WebLocator editableEl1 = new WebLocator(container).setTag("input");
        String type = editableEl1.getAttribute("data-componentid");
        log.debug("active editor type: {}", type);
        if (type.contains("combo")) {
            editor = new ComboBox();
        } else if (type.contains("textarea")) {
            editor = new TextArea();
        } else if (type.contains("datefield")) {
            editor = new DateField();
        } else {
            editor = new TextField();
        }
        editor.setContainer(this).setClasses("x-form-focus").setRenderMillis(1000).setInfoMessage("active editor");
        return (T) editor;
    }
}