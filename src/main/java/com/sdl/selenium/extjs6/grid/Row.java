package com.sdl.selenium.extjs6.grid;

import com.google.common.base.Strings;
import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.AbstractCell;
import com.sdl.selenium.web.utils.RetryUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Row extends com.sdl.selenium.web.table.Row {
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
        this(grid);
        setText(searchElement, searchTypes);
    }

    public Row(WebLocator grid, AbstractCell... cells) {
        this(grid);
        AbstractCell[] childNodes = Stream.of(cells).filter(t -> t != null && (t.getPathBuilder().getText() != null || (t.getPathBuilder().getChildNodes() != null && !t.getPathBuilder().getChildNodes().isEmpty()))).toArray(AbstractCell[]::new);
        setChildNodes(childNodes);
    }

    public Row(WebLocator grid, int indexRow, AbstractCell... cells) {
        this(grid, cells);
        setPosition(indexRow);
    }

    @Override
    public Cell getCell(int columnIndex) {
        return new Cell(this, columnIndex);
    }

    public List<String> getCellsText(int... excludedColumns) {
        return getCellsText((short) 0, excludedColumns);
    }

    public <V> V getCellsText(Class<V> type, int... excludedColumns) {
        return getCellsText(type, (short) 0, excludedColumns);
    }

    public <V> V getCellsText(Class<V> type, short columnLanguages, int... excludedColumns) {
        Class<?> newClazz = null;
        int fieldsCount;
        Class[] parameterTypes = null;
        try {
            newClazz = Class.forName(type.getTypeName());
            fieldsCount = newClazz.getDeclaredFields().length;
            Constructor[] constructors = newClazz.getConstructors();
            for (Constructor c : constructors) {
                if (fieldsCount == c.getParameterCount()) {
                    parameterTypes = c.getParameterTypes();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Class<?> finalNewClazz = newClazz;
        Class[] finalParameterTypes = parameterTypes;
        List<String> cellsText = columnLanguages == 0 ? getCellsText(excludedColumns) : getCellsText(columnLanguages, excludedColumns);
        List<Object> arr = new ArrayList<>(cellsText);
        try {
            Constructor<V> constructor = (Constructor<V>) finalNewClazz.getConstructor(finalParameterTypes);
            return constructor.newInstance(arr.toArray(new Object[0]));
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getCellsText(short columnLanguages, int... excludedColumns) {
        WebLocator columnsEl = new WebLocator(this).setTag("td");
        List<Integer> columns = getColumns(columnsEl.size(), excludedColumns);
        List<String> list = new ArrayList<>();
        for (int j : columns) {
            Cell cell = new Cell(this, j);
            if (columnLanguages == j) {
                list.add(cell.getLanguages());
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

    public void select() {
        scrollInGrid(this);
        if (!isSelected()) {
            doSelect();
        }
    }

    public void unSelect() {
        scrollInGrid(this);
        if (isSelected()) {
            doSelect();
        }
    }

    protected void doSelect() {
        WebLocator checkBox = new WebLocator(this);
        if ("6.7.0".equals(getVersion())) {
            checkBox.setBaseCls("x-selmodel-column");
        } else {
            checkBox.setBaseCls("x-grid-row-checker");
        }
        checkBox.click();
    }

    public boolean isSelected() {
        String aClass = getAttributeClass();
        return !Strings.isNullOrEmpty(aClass) && aClass.contains("x-grid-item-selected");
    }

    public void expand() {
        scrollInGrid(this);
        if (isCollapsed()) {
            doExpanded();
        }
    }

    public void collapse() {
        scrollInGrid(this);
        if (!isCollapsed()) {
            doExpanded();
        }
    }

    protected void doExpanded() {
        WebLocator expander = new WebLocator(this).setBaseCls("x-grid-row-expander");
        expander.click();
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
            return isElementPresent();
        });
    }

    private void scrollInGrid(Row row) {
        while (!row.waitToRender(100L, false)) {
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
            if (!grid.scrollPageDown()) {
                break;
            }
        }
    }
}