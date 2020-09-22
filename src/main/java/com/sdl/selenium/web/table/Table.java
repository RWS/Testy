package com.sdl.selenium.web.table;

import com.sdl.selenium.web.Position;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.CheckBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;

public class Table extends WebLocator implements ITable<Row, Cell> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Table.class);

    private Duration timeout = Duration.ofSeconds(30);

    public Table() {
        setClassName("Table");
        setTag("table");
    }

    public Table(WebLocator container) {
        this();
        setContainer(container);
    }

    @Override
    public boolean rowSelect(String searchText) {
        return rowSelect(searchText, SearchType.EQUALS);
    }

    @Override
    public boolean rowSelect(String searchText, SearchType... searchTypes) {
        ready(true);
        Cell cell = getCell(searchText, searchTypes);
        return doCellSelect(cell);
    }

    private boolean doCellSelect(Cell cell) {
        return doCellAction(cell, null);
    }

    private boolean doCellDoubleClickAt(Cell cell) {
        return doCellAction(cell, "doubleClickAt");
    }

    private boolean doCellAction(Cell cell, String action) {
        boolean selected;
        if ("doubleClickAt".equals(action)) {
            selected = cell.doubleClickAt();
        } else {
            selected = cell.click();
        }
        if (!selected) {
            LOGGER.warn("The element '{}' is not present in the list.", cell);
        }
        return selected;
    }

    /**
     * returns if a table contains a certain element
     *
     * @param searchElement the searchElement of the table element on which the search is done
     * @return if a table contains a certain element
     */
    public boolean isRowPresent(String searchElement) {
        ready();
        Cell cell = getCell(searchElement);
        return cell.isPresent();
    }

    public Number getRowCount(String searchElement, SearchType... searchTypes) {
        ready();
        Cell cell = getCell(searchElement, searchTypes);
        return cell.size();
    }

    public Number getRowCount(String searchElement) {
        return getRowCount(searchElement, SearchType.STARTS_WITH);
    }

    @Override
    public int getCount() {
        if (ready()) {
            WebLocator body = new WebLocator(this).setTag("tbody");
            return new Row(body).size();
        } else {
            LOGGER.warn("table is not ready to be used");
            // TODO could try to verify row count with mask on table or when is disabled also.
            return -1;
        }
    }

    public Row getRow(int rowIndex) {
        return new Row(this, rowIndex).setInfoMessage("row - Table");
    }

    public Row getRow(String searchElement) {
        return new Row(this, searchElement, SearchType.EQUALS);
    }

    public Row getRow(String searchElement, SearchType... searchTypes) {
        return new Row(this, searchElement, searchTypes);
    }

    @Override
    public Cell getCell(int rowIndex, int columnIndex) {
        Row row = getRow(rowIndex);
        return new Cell(row, columnIndex).setInfoMessage("cell - Table");
    }

    @Override
    public Cell getCell(String searchElement) {
        return getCell(searchElement, SearchType.EQUALS);
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

    public Cell getCell(String searchElement, int columnIndex, SearchType... searchTypes) {
        return new Cell(new Row(this, searchElement, searchTypes), columnIndex);
    }

    @Override
    public Row getRow(Cell... byCells) {
        return new Row(this, byCells).setInfoMessage("-Row");
    }

    public Row getRow(int indexRow, Cell... byCells) {
        return new Row(this, indexRow, byCells).setInfoMessage("-Row");
    }

    @Override
    public Cell getCell(int columnIndex, Cell... byCells) {
        return new Cell(getRow(byCells), columnIndex);
    }

    @Override
    public Cell getCell(int columnIndex, String text, Cell... byCells) {
        return new Cell(getRow(byCells), columnIndex, text, SearchType.EQUALS);
    }

    /**
     * get all strings as array from specified columnIndex
     *
     * @param columnIndex columnIndex
     * @return all strings as array from specified columnIndex
     */
    public String[] getColumnTexts(int columnIndex) {
        int count = getCount();
        if (count > 0) {
            String[] texts = new String[count];
            for (int i = 1; i <= count; i++) {
                texts[i - 1] = getText(i, columnIndex);
            }
            return texts;
        } else {
            return null;
        }
    }

    public List<List<String>> getCellsText(int... excludedColumns) {
        WebLocator parentEl = new WebLocator(this).setTag("tbody");
        Row rowsEl = new Row(parentEl);
        Row rowEl = new Row(parentEl).setPosition(Position.LAST);
        Cell columnsEl = new Cell(rowEl);
        int rows = rowsEl.size() + 1;
        int columns = columnsEl.size();

        List<Integer> columnsList = getColumns(columns, excludedColumns);

        if (rows > 0) {
            List<List<String>> listOfList = new ArrayList<>();
            for (int i = 1; i < rows; i++) {
                List<String> list = new ArrayList<>();
                for (int j : columnsList) {
                    list.add(getCell(i, j).getText(true));
                }
                listOfList.add(list);
            }
            return listOfList;
        } else {
            return null;
        }
    }

    public <V> List<V> getCellsText(Class<V> type, int... excludedColumns) {
        List<List<String>> cellsText = getCellsText(excludedColumns);
        if (cellsText == null) {
            return null;
        }
        Class<?> newClazz;
        int size = cellsText.get(0).size();
        Constructor constructor = null;
        try {
            newClazz = Class.forName(type.getTypeName());
            Constructor[] constructors = newClazz.getConstructors();
            for (Constructor c : constructors) {
                int parameterCount = c.getParameterCount();
                if (size == parameterCount) {
                    constructor = c;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        final Constructor <V> finalConstructor = (Constructor<V>)constructor;
        return cellsText.stream().map(t -> {
            try {
                return finalConstructor.newInstance(t.toArray(new Object[0]));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

    protected List<Integer> getColumns(int columns, int[] excludedColumns) {
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

    public String getText(String searchText, int columnId) {
        String text = null;
        Row row = new Row(this, searchText, SearchType.EQUALS);
        if (row.ready()) {
            text = new Cell(row, columnId).getText();
        } else {
            LOGGER.warn("searchText was not found in table: " + searchText);
        }
        return text;
    }

    public String getText(int rowIndex, int columnIndex) {
        return getCell(rowIndex, columnIndex).getText();
    }

    public void select(Row row) {
        CheckBox checkBox = new CheckBox(row);
        boolean selected = checkBox.check(true);
        assertThat("Could not selected " + toString(), selected);
    }

    public void check(Cell... cells) {
        CheckBox checkBox = new CheckBox(getRow(cells));
        boolean selected = checkBox.check(true);
        assertThat("Could not checked " + toString(), selected);
    }

    public void unSelect(Row row) {
        CheckBox checkBox = new CheckBox(row);
        boolean selected = checkBox.check(false);
        assertThat("Could not unselected " + toString(), selected);
    }

    public void unCheck(Cell... cells) {
        CheckBox checkBox = new CheckBox(getRow(cells));
        boolean selected = checkBox.check(false);
        assertThat("Could not unchecked " + toString(), selected);
    }

    public boolean waitToPopulate() {
        return waitToPopulate(timeout);
    }

    @Deprecated
    public boolean waitToPopulate(int seconds) {
        return waitToPopulate(Duration.ofSeconds(seconds * 1000L));
    }

    public boolean waitToPopulate(Duration duration) {
        Row row = getRow(1).setVisibility(true).setInfoMessage("first Row");
        WebLocator body = new WebLocator(this).setTag("tbody"); // TODO see if must add for all rows
        row.setContainer(body);
        return row.waitToRender(duration, false);
    }

    public boolean ready(boolean waitRows) {
        return ready() && (!waitRows || waitToPopulate());
    }
}
