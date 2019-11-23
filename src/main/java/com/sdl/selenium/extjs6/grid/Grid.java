package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.extjs6.form.*;
import com.sdl.selenium.utils.config.WebLocatorConfig;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.Field;
import com.sdl.selenium.web.table.Table;
import com.sdl.selenium.web.utils.RetryUtils;
import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Grid extends Table implements Scrollable {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Grid.class);
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
        int count = 0;
        boolean hasMask;
        long startMs = System.currentTimeMillis();
        while ((hasMask = hasMask()) && (count < seconds)) {
            count++;
            long endMs = System.currentTimeMillis();
            log.info("waitToActivate:" + (endMs - startMs) + " milliseconds; " + this);
            Utils.sleep(500);
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
        return row.waitToRender(seconds * 1000L, false);
    }

    public List<String> getHeaders() {
        WebLocator header = new WebLocator(this).setClasses("x-grid-header-ct");
        String headerText = RetryUtils.retrySafe(4, header::getText);
        return new ArrayList<>(Arrays.asList(headerText.trim().split("\n")));
    }

    private List<List<String>> getLists(int rows, boolean rowExpand, short columnLanguages, List<Integer> columnsList) {
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
        return getCellsText(false, (short) 0, excludedColumns);
    }

    public List<List<String>> getCellsText(short columnLanguages, int... excludedColumns) {
        return getCellsText(false, columnLanguages, excludedColumns);
    }

    public List<List<String>> getCellsText(boolean rowExpand, int... excludedColumns) {
        return getCellsText(rowExpand, (short) 0, excludedColumns);
    }

    public List<List<String>> getCellsText(boolean rowExpand, short columnLanguages, int... excludedColumns) {
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
            return getLists(rows, rowExpand, columnLanguages, columnsList);
        }
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
                            String text = groupElRows.get(i).getCell(j).getText(true).trim();
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
        return getCellsText(type, (short) 0, excludedColumns);
    }

    public <V> List<V> getCellsText(Class<V> type, short columnLanguages, int... excludedColumns) {
        Class<?> newClazz = null;
        int s = 0;
        Class[] parameterTypes = null;
        try {
            newClazz = Class.forName(type.getTypeName());
            s = newClazz.getDeclaredFields().length;
            Constructor[] constructors = newClazz.getConstructors();
            for (Constructor c : constructors) {
                if (s == c.getParameterCount()) {
                    parameterTypes = c.getParameterTypes();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Class<?> finalNewClazz = newClazz;
        int finalS = s;
        Class[] finalParameterTypes = parameterTypes;
        List<List<String>> cellsText = getCellsText(columnLanguages, excludedColumns);
        if (cellsText == null) {
            return null;
        }
        return cellsText.stream().map(t -> {
            List<Object> arr = new ArrayList<>();
            try {
                for (int i = 0; i < finalS; i++) {
                    arr.add(t.get(i));
                }
                Constructor<V> constructor = (Constructor<V>) finalNewClazz.getConstructor(finalParameterTypes);
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

    public <T extends Field> T getEditor(WebLocator cell) {
        return RetryUtils.retry(3, () -> {
            cell.click();
            return getEditor();
        });
    }

    @SuppressWarnings("unchecked")
    public <T extends Field> T getEditor() {
        Field editor;
        WebLocator container = new WebLocator("x-editor", this);
        WebLocator editableEl = new WebLocator(container).setTag("input");
        String type = RetryUtils.retry(2, () -> editableEl.getAttribute("data-componentid"));
        if (type == null) {
            log.error("active editor type: 'null'");
            return null;
        } else {
            if (type.contains("combo")) {
                editor = new ComboBox();
            } else if (type.contains("textarea")) {
                editor = new TextArea();
            } else if (type.contains("datefield")) {
                editor = new DateField();
            } else if (type.contains("tag")) {
                editor = new TagField();
            }else if (type.contains("checkbox")) {
                editor = new CheckBox();
            } else if (type.contains("numberfield") || type.contains("textfield")) {
                editor = new TextField();
            } else {
                log.warn("active editor type: {}", type);
                return null;
            }
        }
        editor.setContainer(this).setClasses("x-form-focus").setRenderMillis(1000).setInfoMessage("active editor");
        return (T) editor;
    }
}