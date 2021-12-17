package com.sdl.selenium.extjs6.grid;

import com.google.common.base.Strings;
import com.sdl.selenium.conditions.ConditionManager;
import com.sdl.selenium.conditions.RenderSuccessCondition;
import com.sdl.selenium.extjs4.window.XTool;
import com.sdl.selenium.extjs6.form.*;
import com.sdl.selenium.utils.config.WebDriverConfig;
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
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Grid extends Table implements Scrollable, XTool {
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

    public Grid(WebLocator container, String title, SearchType... searchTypes) {
        this(container);
        setTitle(title, searchTypes);
    }

    @Override
    public WebLocator getView() {
        return this;
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

    public Row getRow(Boolean isLocked, Cell... byCells) {
        return new Row(this, isLocked, byCells).setInfoMessage("-Row");
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

    @Deprecated
    public boolean waitToActivate(int seconds) {
        return waitToActivate(Duration.ofSeconds(seconds));
    }

    public boolean waitToActivate(Duration duration) {
        boolean hasMask;
        long startMs = System.currentTimeMillis();
        long timeMs = 0L;
        while ((hasMask = hasMask()) && (timeMs < duration.toMillis())) {
            Utils.sleep(500);
            timeMs = System.currentTimeMillis() - startMs;
        }
        long endMs = System.currentTimeMillis();
        log.info("waitToActivate:" + (endMs - startMs) + " milliseconds; " + toString());
        return !hasMask;
    }

    private boolean hasMask() {
        WebLocator mask = new WebLocator(this).setClasses("x-mask").setElPathSuffix("style", "not(contains(@style, 'display: none'))").setAttribute("aria-hidden", "false").setInfoMessage("Mask");
        return mask.waitToRender(Duration.ofMillis(500L), false);
    }

    @Override
    @Deprecated
    public boolean waitToPopulate(int seconds) {
        return waitToPopulate(Duration.ofSeconds(seconds * 1000L));
    }

    @Override
    public boolean waitToPopulate(Duration duration) {
        Row row = getRow(1).setVisibility(true).setRoot("//..//").setInfoMessage("first Row");
        WebLocator body = new WebLocator(this).setClasses("x-grid-header-ct"); // TODO see if must add for all rows
        row.setContainer(body);
        Row row2 = getRow(1).setVisibility(true).setRoot("//..//..//..//../preceding-sibling::*//").setInfoMessage("first Row");
        row2.setContainer(body);
        ConditionManager cm = new ConditionManager(duration);
        cm.add(new RenderSuccessCondition(row)).add(new RenderSuccessCondition(row2));
        return cm.execute().isSuccess();
    }

    public List<String> getHeaders() {
        WebLocator header = new WebLocator(this).setClasses("x-column-header");
        int size = header.size();
        List<String> headers = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            header.setResultIdx(i);
            headers.add(header.getText());
        }
        return headers.stream().filter(i -> !Strings.isNullOrEmpty(i.trim())).collect(Collectors.toList());
    }

    public int getHeadersCount() {
        WebLocator body = new WebLocator(this).setClasses("x-grid-header-ct").setExcludeClasses("x-grid-header-ct-hidden").setResultIdx(1);
        WebLocator header = new WebLocator(body).setClasses("x-column-header");
        return header.size();
    }

    private List<List<String>> getLockedLists(Predicate<Integer> predicate, Function<Cell, String> function, List<Integer> columnsList) {
        WebLocator containerLocked = new WebLocator(this).setClasses("x-grid-scrollbar-clipper", "x-grid-scrollbar-clipper-locked");
        Row rowsEl = new Row(containerLocked);
        int cells = new Row(containerLocked, 1).getCells();
        List<Integer> firstColumns = new ArrayList<>();
        List<Integer> secondColumns = new ArrayList<>();
        for (int i = 0; i < columnsList.size(); i++) {
            int in = columnsList.get(i);
            if (cells > i && in <= cells) {
                firstColumns.add(columnsList.get(i));
            } else {
                secondColumns.add(columnsList.get(i));
            }
        }
        int size = rowsEl.size();
        List<List<String>> listOfList = new ArrayList<>();
        boolean canRead = true;
        String id = "";
        int timeout = 0;
        do {
            for (int i = 1; i <= size; ++i) {
                if (canRead) {
                    List<String> list = new ArrayList<>();
                    Row row = new Row(containerLocked).setTag("tr").setResultIdx(i);
                    for (int j : firstColumns) {
                        Cell cell = new Cell(row, j);
                        String text;
                        if (predicate.test(j)) {
                            text = function.apply(cell);
                        } else {
                            text = cell.getText(true).trim();
                        }
                        list.add(text);
                    }
                    WebLocator containerUnLocked = new WebLocator(this).setClasses("x-grid-scrollbar-clipper").setExcludeClasses("x-grid-scrollbar-clipper-locked");
                    row = new Row(containerUnLocked).setTag("tr").setResultIdx(i);
                    for (int j : secondColumns) {
                        Cell cell = new Cell(row, j - cells);
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
                    Row row = new Row(containerLocked, i);
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
            scrollPageDown();
            scrollPageDown();
            scrollPageDown();
            scrollPageDown();
            scrollPageDown();
            canRead = false;
            timeout++;
        } while (timeout < 30);
        return listOfList;
    }

    public boolean isGridLocked() {
        String aClass = WebDriverConfig.getDriver() == null ? null : this.getAttributeClass();
        return aClass != null && aClass.contains("x-grid-locked");
    }

    private List<List<String>> getLists(int rows, boolean rowExpand, Predicate<Integer> predicate, Function<Cell, String> function, List<Integer> columnsList) {
        Row rowsEl = new Row(this);
        if (!rowExpand) {
            rowsEl.setTag("tr");
        } else {
            rowsEl.setTemplate("visibility", "count(ancestor-or-self::*[contains(@class, 'x-grid-rowbody-tr')]) = 0").setVisibility(true);
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
                            row.setTemplate("visibility", "count(ancestor-or-self::*[contains(@class, 'x-grid-rowbody-tr')]) = 0").setVisibility(true);
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
                    if (rowExpand) {
                        row.setTemplate("visibility", "count(ancestor-or-self::*[contains(@class, 'x-grid-rowbody-tr')]) = 0").setVisibility(true);
                    }
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
            if (rowExpand) {
                row.setTemplate("visibility", "count(ancestor-or-self::*[contains(@class, 'x-grid-rowbody-tr')]) = 0").setVisibility(true);
            }
            id = row.getAttributeId();
            if (rowExpand) {
                scrollPageDown();
                scrollPageDown();
            } else {
                scrollPageDownInTree();
            }
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

    public List<List<String>> getCellsText(Predicate<Integer> predicate, Function<Cell, String> function, int... excludedColumns) {
        return getCellsText(false, predicate, function, excludedColumns);
    }

    public List<List<String>> getCellsText(boolean rowExpand, int... excludedColumns) {
        return getCellsText(rowExpand, (short) 0, excludedColumns);
    }

    public List<List<String>> getCellsText(boolean rowExpand, short columnLanguages, int... excludedColumns) {
        return getCellsText(rowExpand, t -> t == columnLanguages, Cell::getLanguages, excludedColumns);
    }

    public List<List<String>> getCellsText(boolean rowExpand, Predicate<Integer> predicate, Function<Cell, String> function, int... excludedColumns) {
        Row rowsEl = new Row(this).setTag("tr");
        Row rowEl = new Row(this, 1);
        if (rowExpand) {
            rowsEl.setTemplate("visibility", "count(ancestor-or-self::*[contains(@class, 'x-grid-rowbody-tr')]) = 0").setVisibility(true);
            rowEl = new Row(this).setTag("tr").setTemplate("visibility", "count(ancestor-or-self::*[contains(@class, 'x-grid-rowbody-tr')]) = 0").setVisibility(true).setResultIdx(1);
        }
        Cell columnsEl = new Cell(rowEl);
        int rows = rowsEl.size();
        int columns = columnsEl.size();
        final List<Integer> columnsList = getColumns(columns, excludedColumns);
        if (rows <= 0) {
            return null;
        } else {
            if (getAttributeClass().contains("x-grid-locked")) {
                return getLockedLists(predicate, function, columnsList);
            } else {
                return getLists(rows, rowExpand, predicate, function, columnsList);
            }
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
        return getCellsText(type, false, (short) 0, excludedColumns);
    }

    public <V> List<V> getCellsText(Class<V> type, boolean expandRow, int... excludedColumns) {
        return getCellsText(type, expandRow, (short) 0, excludedColumns);
    }

    public <V> List<V> getCellsText(Class<V> type, short columnLanguages, int... excludedColumns) {
        return getCellsText(type, false, t -> t == columnLanguages, Cell::getLanguages, excludedColumns);
    }

    public <V> List<V> getCellsText(Class<V> type, boolean expandRow, short columnLanguages, int... excludedColumns) {
        return getCellsText(type, expandRow, t -> t == columnLanguages, Cell::getLanguages, excludedColumns);
    }

    public <V> List<V> getCellsText(Class<V> type, Predicate<Integer> predicate, Function<Cell, String> function, int... excludedColumns) {
        return getCellsText(type, false, predicate, function, excludedColumns);
    }

    public <V> List<V> getCellsText(Class<V> type, boolean expandRow, Predicate<Integer> predicate, Function<Cell, String> function, int... excludedColumns) {
        List<List<String>> cellsText = getCellsText(expandRow, predicate, function, excludedColumns);
        if (cellsText == null) {
            return null;
        }
        Class<?> newClazz;
        int size = cellsText.get(0).size();
        Constructor<?> constructor = null;
        try {
            newClazz = Class.forName(type.getTypeName());
            Constructor<?>[] constructors = newClazz.getConstructors();
            for (Constructor<?> c : constructors) {
                int parameterCount = c.getParameterCount();
                if (size == parameterCount) {
                    constructor = c;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        final Constructor<V> finalConstructor = (Constructor<V>) constructor;
        return cellsText.stream().map(t -> {
            try {
                return finalConstructor.newInstance(t.toArray());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
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
            } else if (type.contains("checkbox")) {
                editor = new CheckBox();
            } else if (type.contains("numberfield") || type.contains("textfield")) {
                editor = new TextField();
            } else {
                log.warn("active editor type: {}", type);
                return null;
            }
        }
        editor.setContainer(this).setRender(Duration.ofSeconds(1)).setInfoMessage("active editor");
        if (!(editor instanceof CheckBox)) {
            editor.setClasses("x-form-focus");
        }
        return (T) editor;
    }

    public WebLocator getEmptyEl(String title, String message) {
        WebLocator titleEL = new WebLocator().setClasses("x-grid-empty-title").setText(title);
        WebLocator content = new WebLocator().setClasses("x-grid-empty-text").setText(message);
        return new WebLocator(this).setClasses("x-grid-empty").setChildNodes(content, titleEL);
    }
}