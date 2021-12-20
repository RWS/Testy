package com.sdl.selenium.extjs6.grid;

import com.google.common.base.Strings;
import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.AbstractCell;
import com.sdl.selenium.web.utils.RetryUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Row extends com.sdl.selenium.web.table.Row {
    private static final Logger log = LogManager.getLogger(Row.class);
    private String version;

    public Row() {
        super();
        setTag("table");
    }

    public Row(WebLocator grid) {
        this();
        setContainer(grid);
    }

    public Row(WebLocator grid, int indexRow) {
        this(grid);
        setPosition(indexRow);
    }

    public Row(WebLocator grid, String searchElement, SearchType... searchTypes) {
        this();
        setText(searchElement, searchTypes);
        if (isGridLocked(grid)) {
            String index = getAttribute("data-recordindex");
            setTag("*");
            setElPath(getXPath() + "//table[@data-recordindex='" + index + "']");
        }
        setContainer(grid);
    }

    private boolean isGridLocked(WebLocator grid) {
        String aClass = WebDriverConfig.getDriver() == null ? null : grid.getAttributeClass();
        return aClass != null && aClass.contains("x-grid-locked");
    }

    public Row(WebLocator grid, AbstractCell... cells) {
        this(grid, false, cells);
    }

    public Row(WebLocator grid, boolean size, AbstractCell... cells) {
        this();
        AbstractCell[] childNodes = Stream.of(cells).filter(t -> t != null && (t.getPathBuilder().getText() != null || (t.getPathBuilder().getChildNodes() != null && !t.getPathBuilder().getChildNodes().isEmpty()))).toArray(AbstractCell[]::new);
        if (isGridLocked(grid)) {
            Integer index = null;
            List<Set<Integer>> ids = new ArrayList<>();
            for (AbstractCell cell : childNodes) {
                ((Grid) grid).scrollTop();
                int indexCurrent = getChildNodePosition(((Grid) grid), cell);
                cell.setTemplateValue("tagAndPosition", indexCurrent + "");
                WebLocator tmpEl = new WebLocator(grid).setTag("table").setChildNodes(cell);
                boolean isScrollBottom;
                Set<Integer> list = new LinkedHashSet<>();
                do {
                    int count = tmpEl.size();
                    for (int j = 1; j <= count; j++) {
                        tmpEl.setResultIdx(j);
                        String indexValue = tmpEl.getAttribute("data-recordindex");
                        int i1 = Integer.parseInt(indexValue);
                        list.add(i1);
                    }
                    isScrollBottom = ((Grid) grid).isScrollBottom();
                    if (!isScrollBottom) {
                        ((Grid) grid).scrollPageDown();
                        ((Grid) grid).scrollPageDown();
                        ((Grid) grid).scrollPageDown();
                        ((Grid) grid).scrollPageDown();
                    }
                    tmpEl.setResultIdx(0);
                } while (!isScrollBottom);
                ids.add(list);
            }
            ((Grid) grid).scrollTop();
            List<Integer> theMinList = getTheMinList(ids);
            ids.remove(theMinList);
            List<Integer> commonId = findCommonId(ids, theMinList);
            if (commonId.size() == 1) {
                index = commonId.get(0);
            } else {
                log.error("Find more row that one!!!");
            }
            for (AbstractCell childNode : childNodes) {
                if (size) {
                    childNode.setTag("td");
                } else {
                    childNode.setTag("table[@data-recordindex='" + index + "']//td");
                }
            }
            setChildNodes(childNodes);
            if (size) {
                setTag("table");
            } else {
                setTag("*");
                setElPath(getXPath() + "//table[@data-recordindex='" + index + "']");
            }
        } else {
            setChildNodes(childNodes);
        }
        setContainer(grid);
    }

    private List<Integer> findCommonId(List<Set<Integer>> ids, List<Integer> theMinList) {
        for (Set<Integer> idList : ids) {
            theMinList.retainAll(idList);
        }
        return theMinList;
    }

    private static List<Integer> getTheMinList(List<Set<Integer>> lists) {
        Optional<Set<Integer>> min = lists.stream()
                .min(Comparator.comparingInt(Set::size));
        return min.<List<Integer>>map(ArrayList::new).orElseGet(ArrayList::new);
    }

    private int getChildNodePosition(Grid grid, AbstractCell cell) {
        int firstColumns = getLockedCells(grid);
        String positions = cell.getPathBuilder().getTemplatesValues().get("tagAndPosition")[0];
        int actualPosition = Integer.parseInt(positions);
        if (actualPosition <= firstColumns) {
            return actualPosition;
        } else {
            return actualPosition - firstColumns;
        }
    }

    public Row(WebLocator grid, int indexRow, AbstractCell... cells) {
        this(grid, cells);
        setPosition(indexRow);
    }

    @Override
    public Cell getCell(int columnIndex) {
        WebLocator grid = getPathBuilder().getContainer();
        if (isGridLocked(grid)) {
            int firstColumns = getLockedCells(grid);
            int position = 1;
            if (columnIndex > firstColumns) {
                columnIndex = columnIndex - firstColumns;
                position = 2;
            }
            return new Cell(this, columnIndex).setResultIdx(position);
        } else {
            return new Cell(this, columnIndex);
        }
    }

    private int getLockedCells(WebLocator grid) {
        WebLocator containerLocked = new WebLocator(grid).setClasses("x-grid-scrollbar-clipper", "x-grid-scrollbar-clipper-locked");
        return new Row(containerLocked, 1).getCells();
    }

    public List<String> getCellsText(int... excludedColumns) {
        return getCellsText((short) 0, excludedColumns);
    }

    public <V> V getCellsText(Class<V> type, int... excludedColumns) {
        return getCellsText(type, (short) 0, excludedColumns);
    }

    public <V> V getCellsText(Class<V> type, short columnLanguages, int... excludedColumns) {
        return getCellsText(type, t -> t == columnLanguages, Cell::getLanguages, excludedColumns);
    }

    public <V> V getCellsText(Class<V> type, Predicate<Integer> predicate, Function<Cell, String> function, int... excludedColumns) {
        List<String> cellsText = getCellsText(predicate, function, excludedColumns);
        int fieldsCount;
        Constructor constructor = null;
        try {
            Class<?> newClazz = Class.forName(type.getTypeName());
            fieldsCount = cellsText.size();
            Constructor[] constructors = newClazz.getConstructors();
            for (Constructor c : constructors) {
                if (fieldsCount == c.getParameterCount()) {
                    constructor = c;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Constructor<V> constructorTemp = (Constructor<V>) constructor;
            return constructorTemp.newInstance(cellsText.toArray(new Object[0]));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getCellsText(short columnLanguages, int... excludedColumns) {
        return getCellsText(t -> t == columnLanguages, Cell::getLanguages, excludedColumns);
    }

    public List<String> getCellsText(Predicate<Integer> predicate, Function<Cell, String> function, int... excludedColumns) {
        WebLocator columnsEl = new WebLocator(this).setTag("td");
        List<Integer> columns = getColumns(columnsEl.size(), excludedColumns);
        List<String> list = new ArrayList<>();
        for (int j : columns) {
            Cell cell = new Cell(this, j);
            if (predicate.test(j)) {
                list.add(function.apply(cell));
            } else {
                list.add(cell.getText().trim());
            }
        }
        return list;
    }

    private String getVersion() {
        if (version == null) {
            Grid grid = (Grid) getPathBuilder().getContainer();
            if (grid != null) {
                this.version = grid.getVersion();
            }
        }
        return version;
    }

    public <T extends Row> T setVersion(String version) {
        this.version = version;
        return (T) this;
    }

    public boolean select() {
        scrollInGrid(this);
        return isSelected() || selectPrivate() && isSelected();
    }

    public boolean doSelect() {
        scrollInGrid(this);
        return isSelected() || doSelectPrivate() && isSelected();
    }

    public boolean unSelect() {
        scrollInGrid(this);
        return !isSelected() || selectPrivate() && !isSelected();
    }

    public boolean doUnSelect() {
        scrollInGrid(this);
        return !isSelected() || doSelectPrivate() && !isSelected();
    }

    private boolean selectPrivate() {
        WebLocator checkBox = new WebLocator(this);
        if ("6.7.0".equals(getVersion())) {
            checkBox.setBaseCls("x-selmodel-column");
        } else {
            checkBox.setBaseCls("x-grid-row-checker");
        }
        return checkBox.click();
    }

    private boolean doSelectPrivate() {
        WebLocator checkBox = new WebLocator(this);
        if ("6.7.0".equals(getVersion())) {
            checkBox.setBaseCls("x-selmodel-column");
        } else {
            checkBox.setBaseCls("x-grid-row-checker");
        }
        return checkBox.doClick();
    }

    public boolean isSelected() {
        String aClass = getAttributeClass();
        return !Strings.isNullOrEmpty(aClass) && aClass.contains("x-grid-item-selected");
    }

    public boolean expand() {
        scrollInGrid(this);
        return !isCollapsed() || RetryUtils.retry(2, () -> doExpanded() && !isCollapsed());
    }

    public boolean collapse() {
        scrollInGrid(this);
        return isCollapsed() || RetryUtils.retry(2, () -> doExpanded() && isCollapsed());
    }

    protected boolean doExpanded() {
        WebLocator expander = new WebLocator(this).setBaseCls("x-grid-row-expander");
        return expander.doClick();
    }

    public boolean isCollapsed() {
        String aClass = getAttributeClass();
        return !Strings.isNullOrEmpty(aClass) && aClass.contains("x-grid-row-collapsed");
    }

    public boolean scrollTo() {
        return RetryUtils.retryIfNotSame(70, true, () -> {
            WebLocator container = getPathBuilder().getContainer();
            int lastRowVisibleIndex = new Row(container).findElements().size() - 1;
            Row row = new Row(container, lastRowVisibleIndex);
            WebLocatorUtils.scrollToWebLocator(row);
            return isPresent();
        });
    }

    private void scrollInGrid(Row row) {
        Grid grid;
        if (getPathBuilder().getContainer() instanceof Grid) {
            grid = (Grid) getPathBuilder().getContainer();
        } else {
            if (getPathBuilder().getContainer().getPathBuilder().getContainer() instanceof Grid) {
                grid = (Grid) getPathBuilder().getContainer().getPathBuilder().getContainer();
            } else {
                grid = (Grid) getPathBuilder().getContainer().getPathBuilder().getContainer().getPathBuilder().getContainer();
            }
        }
        if (grid.isScrollBottom()) {
            grid.scrollTop();
        }
        while (!row.waitToRender(Duration.ofMillis(100), false)) {
            if (!grid.scrollPageDown()) {
                break;
            }
        }
    }

    public int getCells() {
        return new Cell(this).size();
    }
}