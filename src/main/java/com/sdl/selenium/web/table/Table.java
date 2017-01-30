package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.CheckBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Table extends WebLocator implements ITable<Row, Cell> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Table.class);

    private int timeout = 30;

    public Table() {
        withClassName("Table");
        withTag("table");
    }

    public Table(WebLocator container) {
        this();
        withContainer(container);
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
        if (selected) {
            LOGGER.info("The element '" + cell + "' has been located.");
        } else {
            LOGGER.warn("The element '" + cell + "' is not present in the list.");
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Path's element is: " + cell.getXPath());
                LOGGER.debug("Total Rows: " + getCount());
            }
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
        return cell.isElementPresent();
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
            WebLocator body = new WebLocator(this).withTag("tbody");
            return new Row(body).size();
        } else {
            LOGGER.warn("table is not ready to be used");
            // TODO could try to verify row count with mask on table or when is disabled also.
            return -1;
        }
    }

    public Row getRow(int rowIndex) {
        return new Row(this, rowIndex).withInfoMessage("row - Table");
    }

    public Row getRow(String searchElement) {
        return new Row(this, searchElement, SearchType.EQUALS);
    }

    public Row getRow(String searchElement, SearchType... searchTypes) {
        return new Row(this, searchElement, searchTypes);
    }

    @Override
    public TableCell getCell(int rowIndex, int columnIndex) {
        Row row = getRow(rowIndex);
        return new TableCell(row, columnIndex).withInfoMessage("cell - Table");
    }

    @Override
    public TableCell getCell(String searchElement) {
        return getCell(searchElement, SearchType.EQUALS);
    }

    @Override
    public TableCell getCell(String searchElement, SearchType ...searchTypes) {
        Row row = new Row(this);
        return new TableCell(row).withText(searchElement, searchTypes);
    }

    /**
     * @deprecated use {@link #getCell(int, int, String)}
     * @param rowIndex row index
     * @param columnIndex colum index
     * @param text text
     * @return TableCell
     */
    public TableCell getTableCell(int rowIndex, int columnIndex, String text) {
        Row row = getRow(rowIndex);
        String selector = new Cell().withText(text, SearchType.EQUALS).getXPath();
        return new TableCell(row).withElxPath(selector + "[" + columnIndex + "]");
    }

    public Cell getCell(int rowIndex, int columnIndex, String text) {
        Row row = getRow(rowIndex);
        String selector = new Cell().withText(text, SearchType.EQUALS).getXPath();
        return new Cell(row).withElxPath(selector + "[" + columnIndex + "]");
    }

    /**
     * @deprecated use {@link #getCell(String, String, SearchType[])}
     * @param searchElement text
     * @param columnText text
     * @param searchTypes see {@link SearchType}
     * @return TableCell
     */
    public TableCell getTableCell(String searchElement, String columnText, SearchType... searchTypes) {
        Row row = getRow(searchElement, SearchType.CONTAINS);
        return new TableCell(row).withText(columnText, searchTypes);
    }

    public Cell getCell(String searchElement, String columnText, SearchType... searchTypes) {
        Row row = getRow(searchElement, SearchType.CONTAINS);
        return new Cell(row).withText(columnText, searchTypes);
    }

    /**
     * @deprecated use {@link #getCell(String, int, SearchType[])}
     * @param searchElement text
     * @param columnIndex column index
     * @param searchTypes see {@link SearchType}
     * @return TableCell
     */
    public TableCell getTableCell(String searchElement, int columnIndex, SearchType... searchTypes) {
        return new TableCell(new Row(this, searchElement, searchTypes), columnIndex);
    }

    public Cell getCell(String searchElement, int columnIndex, SearchType... searchTypes) {
        return new Cell(new Row(this, searchElement, searchTypes), columnIndex);
    }

    @Override
    public Row getRow(Cell... byCells) {
        return new Row(this, byCells).withInfoMessage("-Row");
    }

    public Row getRow(int indexRow, Cell... byCells) {
        return new Row(this, indexRow, byCells).withInfoMessage("-Row");
    }

    /**
     * @deprecated use {@link #getCell(int, Cell...)}
     * @param columnIndex column index
     * @param byCells TableCell
     * @return TableCell
     */
    public TableCell getCell(int columnIndex, TableCell... byCells) {
        return new TableCell(getRow(byCells), columnIndex);
    }

    @Override
    public Cell getCell(int columnIndex, Cell... byCells) {
        return new Cell(getRow(byCells), columnIndex);
    }

    /**
     * @deprecated use {@link #getCell(int, String, Cell...)}
     * @param columnIndex column index
     * @param text text
     * @param byCells TableCell
     * @return TableCell
     */
    public TableCell getCell(int columnIndex, String text, TableCell... byCells) {
        return new TableCell(getRow(byCells), columnIndex, text, SearchType.EQUALS);
    }

    @Override
    public Cell getCell(int columnIndex, String text, Cell... byCells) {
        return new Cell(getRow(byCells), columnIndex, text, SearchType.EQUALS);
    }

    /**
     * returns all text elements from a table
     *
     * @param searchText searchText
     * @return all text elements from a table
     * @deprecated //TODO fix it
     */

    public String[] getRowText(String searchText) {
        String[] rowElements = null;
        String text = getRow(searchText).getText();
        if (text != null) {
            rowElements = text.split("\n");
        }
        return rowElements;
    }

    /**
     * get all strings as array from specified columnIndex
     *
     * @param columnIndex columnIndex
     * @return all strings as array from specified columnIndex
     */
    public String[] getCollTexts(int columnIndex) {
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

    public List<List<String>> getCellsText() {
        WebLocator parentEl = new WebLocator(this).withTag("tbody");
        Row rowsEl = new Row(parentEl);
        Row rowEl = new Row(parentEl, 1);
        Cell columnsEl = new Cell(rowEl);
        int rows = rowsEl.size() + 1;
        int columns = columnsEl.size() + 1;

        if (rows > 0) {
            List<List<String>> listOfList = new ArrayList<>();
            for (int i = 1; i < rows; i++) {
                List<String> list = new ArrayList<>();
                for (int j = 1; j < columns; j++) {
                    list.add(getCell(i, j).getText());
                }
                listOfList.add(list);
            }
            return listOfList;
        } else {
            return null;
        }
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

    /**
     * returns if a specific Table contains a certain element
     *
     * @param searchText  the element that is already part of the table
     * @param columnIndex the column index where the comparison is done (STARTS AT 0)
     * @param compareText the text to which the element found is compared to
     * @return if a specific Table contains a certain element
     */
    public boolean isTextPresent(String searchText, int columnIndex, String compareText) {
        String text = getText(searchText, columnIndex);
        return text != null && text.trim().equals(compareText);
    }

    public boolean check(String searchText, int columnIndex) {
        return check(searchText, columnIndex, SearchType.EQUALS);
    }

    public boolean check(String searchText, int columnIndex, SearchType... searchTypes) {
        boolean selected = false;
        if (ready(true)) {
            CheckBox checkBox = new CheckBox().withContainer(getCell(searchText, columnIndex, searchTypes));
            selected = checkBox.isSelected() || checkBox.click();
        }
        return selected;
    }

    public boolean unCheck(String searchText, int columnIndex) {
        return unCheck(searchText, columnIndex, SearchType.EQUALS);
    }

    public boolean unCheck(String searchText, int columnIndex, SearchType... searchTypes) {
        boolean selected = false;
        if (ready(true)) {
            CheckBox checkBox = new CheckBox().withContainer(getCell(searchText, columnIndex, searchTypes));
            selected = !checkBox.isSelected() || checkBox.click();
        }
        return selected;
    }

    public boolean waitToPopulate() {
        return waitToPopulate(timeout);
    }

    public boolean waitToPopulate(int seconds) {
        Row row = getRow(1).withVisibility(true).withInfoMessage("first Row");
        WebLocator body = new WebLocator(this).withTag("tbody"); // TODO see if must add for all rows
        row.withContainer(body);
        return row.waitToRender(seconds * 1000L);
    }

    public boolean ready(boolean waitRows) {
        return ready() && (!waitRows || waitToPopulate());
    }
}
