package com.sdl.selenium.extjs6.grid;

import com.google.common.base.Strings;
import com.sdl.selenium.conditions.ConditionManager;
import com.sdl.selenium.conditions.RenderSuccessCondition;
import com.sdl.selenium.extjs4.window.XTool;
import com.sdl.selenium.extjs6.form.CheckBox;
import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.utils.config.WebLocatorConfig;
import com.sdl.selenium.web.Editor;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.Transform;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.Table;
import com.sdl.selenium.web.utils.Utils;
import lombok.SneakyThrows;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Grid extends Table implements Scrollable, XTool, Editor, Transform {
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
            WebLocator headerEL = new WebLocator(this).setClasses("x-column-header").setText(headers[i], SearchType.DEEP_CHILD_NODE_OR_SELF, SearchType.EQUALS);
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

    public Row getRow(boolean size, Cell... byCells) {
        return new Row(this, size, byCells).setInfoMessage("-Row");
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
        log.info("waitToActivate:{} milliseconds; {}", endMs - startMs, this);
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
        WebLocator body = new WebLocator(this).setClasses("x-grid-header-ct").setExcludeClasses("x-grid-header-ct-hidden").setResultIdx(1);
        WebLocator header = new WebLocator(body).setClasses("x-column-header");
        int size = header.size();
        List<String> headers = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            header.setResultIdx(i);
            headers.add(header.getText());
        }
        return headers.stream().filter(i -> !Strings.isNullOrEmpty(i.trim())).collect(Collectors.toList());
    }

    public List<String> getHeadersFast() {
        WebLocator body = new WebLocator(this).setClasses("x-grid-header-ct").setExcludeClasses("x-grid-header-ct-hidden").setResultIdx(1);
        ArrayList<String> headers = new ArrayList<>();
        if (!Strings.isNullOrEmpty(body.getText())) {
            headers.addAll(Arrays.asList(body.getText().split("\\n")));
        }
        return headers;
    }

    public int getHeadersCount() {
        if (isGridLocked()) {
            Row row = getRow(1);
            WebLocator columnsEl = new WebLocator(row).setTag("td");
            return columnsEl.size();
        } else {
            WebLocator body = new WebLocator(this).setClasses("x-grid-header-ct").setExcludeClasses("x-grid-header-ct-hidden").setResultIdx(1);
            WebLocator header = new WebLocator(body).setClasses("x-column-header").setAttribute("aria-hidden", "false");
            return header.size();
        }
    }

    public List<List<String>> getLockedLists(Predicate<Integer> predicate, Function<Cell, String> function, List<Integer> columnsList) {
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
                scrollTop();
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

    private short getCellPosition(int actualPosition) {
        int firstColumns = getLockedCells();
        if (actualPosition <= firstColumns) {
            return (short) actualPosition;
        } else {
            return (short) (actualPosition - firstColumns);
        }
    }

    private int getLockedCells() {
        WebLocator containerLocked = new WebLocator(this).setClasses("x-grid-scrollbar-clipper", "x-grid-scrollbar-clipper-locked");
        return new Row(containerLocked, 1).getCells();
    }

    public <V> List<List<String>> getLists(int rows, Options<V> options, List<Integer> columnsList) {
        Row rowsEl = new Row(this);
        if (!options.isExpand()) {
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
                    List<String> list = options.getCollector() == null ? collector(options, columnsList, this, i) : options.getCollector().apply(new Details<>(options, columnsList, this, i));
                    listOfList.add(list);
                } else {
                    if (size == i + 1) {
                        break;
                    }
                    Row row = new Row(this, i);
                    if (options.isExpand()) {
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
            if (options.isExpand()) {
                row.setTemplate("visibility", "count(ancestor-or-self::*[contains(@class, 'x-grid-rowbody-tr')]) = 0").setVisibility(true);
            }
            id = row.getAttributeId();
            if (options.isExpand()) {
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

    private <V> List<String> collector(Options<V> options, List<Integer> columnsList, Grid grid, int i) {
        Row row = new Row(grid).setTag("tr").setResultIdx(i);
        if (options.isExpand()) {
            row.setTemplate("visibility", "count(ancestor-or-self::*[contains(@class, 'x-grid-rowbody-tr')]) = 0").setVisibility(true);
        }
        List<String> list = row.getValues(options, columnsList);
        return list;
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
        if (rowExpand) {
            rowsEl.setTemplate("visibility", "count(ancestor-or-self::*[contains(@class, 'x-grid-rowbody-tr')]) = 0").setVisibility(true);
        }
        int rows = rowsEl.size();
        final List<Integer> columnsList = getColumns(excludedColumns);
        if (rows <= 0) {
            return null;
        } else {
            if (isGridLocked()) {
                return getLockedLists(predicate, function, columnsList);
            } else {
                Options<?> options = new Options<>(rowExpand, predicate, function);
                return getLists(rows, options, columnsList);
            }
        }
    }

    public <V> List<List<String>> getCellsTexts(Options<V> options, int... excludedColumns) {
        Row rowsEl = new Row(this).setTag("tr");
        if (options.isExpand()) {
            rowsEl.setTemplate("visibility", "count(ancestor-or-self::*[contains(@class, 'x-grid-rowbody-tr')]) = 0").setVisibility(true);
        }
        int rows = rowsEl.size();
        final List<Integer> columnsList = getColumns(excludedColumns);
        if (rows <= 0) {
            return null;
        } else {
            if (isGridLocked()) {
                return getLockedLists(options.getPredicate(), options.getFunction(), columnsList);
            } else {
                return getLists(rows, options, columnsList);
            }
        }
    }

    public List<List<String>> getCellsText(String group, int... excludedColumns) {
        return getCellsText(group, t -> t == 0, null, excludedColumns);
    }

    public <V> List<V> getCellsValues(V type, String group, int... excludedColumns) {
        List<List<String>> actualValues = getCellsText(group, t -> t == 0, null, excludedColumns);
        List<Integer> columnsList = Arrays.stream(excludedColumns).boxed().toList();
        List<V> collect = transformTo(type, actualValues, columnsList);
        return collect;
    }

    public <V> List<V> getCellsValues(V type, String group, Predicate<Integer> predicate, Function<Cell, String> function, int... excludedColumns) {
        List<List<String>> actualValues = getCellsText(group, predicate, function, excludedColumns);
        List<Integer> columnsList = Arrays.stream(excludedColumns).boxed().toList();
        List<V> collect = transformTo(type, actualValues, columnsList);
        return collect;
    }

    public List<List<String>> getCellsText(String group, Predicate<Integer> predicate, Function<Cell, String> function, int... excludedColumns) {
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
                            String text;
                            Cell cell = groupElRows.get(i).getCell(j);
                            if (predicate.test(j)) {
                                text = function.apply(cell);
                            } else {
                                text = cell.getText(true).trim();
                            }
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

    /**
     * @deprecated use {@link Grid#getCellsValues(Object, int...)}
     */
    @Deprecated
    public <V> List<V> getCellsText(Class<V> type, int... excludedColumns) {
        return getCellsText(type, false, (short) 0, excludedColumns);
    }

    public <V> List<V> getCellsValues(V type, int... excludedColumns) {
        return getCellsValues(type, false, (short) 0, excludedColumns);
    }

    /**
     * @deprecated use {@link Grid#getCellsValues(Object, boolean, int...)}
     */
    @Deprecated
    public <V> List<V> getCellsText(Class<V> type, boolean expandRow, int... excludedColumns) {
        return getCellsText(type, expandRow, (short) 0, excludedColumns);
    }

    public <V> List<V> getCellsValues(V type, boolean expandRow, int... excludedColumns) {
        return getCellsValues(type, expandRow, (short) 0, excludedColumns);
    }

    /**
     * @deprecated use {@link Grid#getCellsValues(Object, short, int...)}
     */
    @Deprecated
    public <V> List<V> getCellsText(Class<V> type, short columnLanguages, int... excludedColumns) {
        return getCellsText(type, false, t -> t == columnLanguages, Cell::getLanguages, excludedColumns);
    }

    public <V> List<V> getCellsValues(V type, short columnLanguages, int... excludedColumns) {
        return getCellsValues(type, false, t -> t == columnLanguages, Cell::getLanguages, excludedColumns);
    }

    /**
     * @deprecated use {@link Grid#getCellsValues(Object, boolean, short, int...)}
     */
    @Deprecated
    public <V> List<V> getCellsText(Class<V> type, boolean expandRow, short columnLanguages, int... excludedColumns) {
        return getCellsText(type, expandRow, t -> t == columnLanguages, Cell::getLanguages, excludedColumns);
    }

    public <V> List<V> getCellsValues(V type, boolean expandRow, short columnLanguages, int... excludedColumns) {
        return getCellsValues(type, expandRow, t -> t == columnLanguages, Cell::getLanguages, excludedColumns);
    }

    /**
     * @deprecated use {@link Grid#getCellsValues(Object, Predicate, Function, int...)}
     */
    @Deprecated
    public <V> List<V> getCellsText(Class<V> type, Predicate<Integer> predicate, Function<Cell, String> function, int... excludedColumns) {
        return getCellsText(type, false, predicate, function, excludedColumns);
    }

    public <V> List<V> getCellsValues(V type, Predicate<Integer> predicate, Function<Cell, String> function, int... excludedColumns) {
        return getCellsValues(type, false, predicate, function, excludedColumns);
    }

    /**
     * @deprecated use {@link Grid#getCellsValues(Object, boolean, Predicate, Function, int...)}
     */
    @Deprecated
    @SneakyThrows
    public <V> List<V> getCellsText(Class<V> type, boolean expandRow, Predicate<Integer> predicate, Function<Cell, String> function, int... excludedColumns) {
        List<List<String>> cellsText = getCellsText(expandRow, predicate, function, excludedColumns);
        if (cellsText == null) {
            return null;
        }
        List<V> actualValue = transformToObjectList(type, cellsText);
        return actualValue;
    }

    @SneakyThrows
    public <V> List<V> getCellsValues(V type, boolean expandRow, Predicate<Integer> predicate, Function<Cell, String> function, int... excludedColumns) {
        List<List<String>> cellsText = getCellsText(expandRow, predicate, function, excludedColumns);
        if (cellsText == null) {
            return null;
        }
        List<Integer> columnsList = Arrays.stream(excludedColumns).boxed().toList();
        List<V> actualValue = transformTo(type, cellsText, columnsList);
        return actualValue;
    }

    @SneakyThrows
    public <V> List<V> getCellsValues(Options<V> options, int... excludedColumns) {
        List<List<String>> cellsText = getCellsTexts(options, excludedColumns);
        if (cellsText == null) {
            return null;
        }
        List<Integer> columnsList = Arrays.stream(excludedColumns).boxed().toList();
        List<V> actualValue = transformTo(options.getType(), cellsText, columnsList);
        return actualValue;
    }

    @Override
    public int getCount() {
        return new Row(this).size();
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

    public boolean selectAll() {
        return selectAll(true);
    }

    public boolean selectAll(boolean check) {
        CheckBox checkBox = getCheckBox();
        return checkBox.check(check);
    }

    public boolean doSelectAll(Boolean check) {
        CheckBox checkBox = getCheckBox();
        return checkBox.doCheck(check);
    }

    private CheckBox getCheckBox() {
        WebLocator columnHeader = new WebLocator(this).setClasses("x-column-header");
        CheckBox checkBox = new CheckBox(columnHeader) {
            @Override
            public boolean isChecked() {
                String aClass = columnHeader.getAttributeClass();
                return aClass != null && aClass.contains("x-grid-hd-checker-on");
            }
        }.setBaseCls("x-column-header-checkbox");
        checkBox.setTag("*").setType(null);
        return checkBox;
    }

    public WebLocator getEmptyEl(String title, String message) {
        WebLocator titleEL = new WebLocator().setClasses("x-grid-empty-title").setText(title);
        WebLocator content = new WebLocator().setClasses("x-grid-empty-text").setText(message);
        return new WebLocator(this).setClasses("x-grid-empty").setChildNodes(content, titleEL);
    }

    public List<List<String>> getParallelValues(Predicate<Integer> predicate, Function<Cell, String> function, int... excludedColumns) {
        Row rowsEl = new Row(this).setTag("tr");
        List<Integer> columnsList = getColumns(excludedColumns);
        int size = rowsEl.size();
        List<List<String>> listOfList = new ArrayList<>();
        boolean canRead = true;
        String id = "";
        int timeout = 0;

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<List<String>>> futures = new ArrayList<>();

        do {
            for (int i = 1; i <= size; ++i) {
                if (canRead) {
                    int finalI = i;
                    Future<List<String>> future = executorService.submit(() -> getRowValues(predicate, function, columnsList, finalI));
                    futures.add(future);
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

            for (Future<List<String>> future : futures) {
                try {
                    listOfList.add(future.get());
                } catch (InterruptedException | ExecutionException e) {
                    // Tratarea erorilor sau înregistrarea lor
                    e.printStackTrace();
                }
            }

            futures.clear();

            if (isScrollBottom()) {
                break;
            }

            Row row = new Row(this, size);
            id = row.getAttributeId();
            scrollPageDownInTree();
            canRead = false;
            timeout++;
        } while (timeout < 30);

        executorService.shutdown();

        return listOfList;
    }

    private List<String> getRowValues(Predicate<Integer> predicate, Function<Cell, String> function, List<Integer> columnsList, int finalI) {
        Row row = new Row(this).setTag("tr").setResultIdx(finalI);
        return row.getValues(predicate, function, columnsList);
    }
}